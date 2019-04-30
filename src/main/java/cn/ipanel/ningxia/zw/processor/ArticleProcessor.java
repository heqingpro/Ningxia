package cn.ipanel.ningxia.zw.processor;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.config.SysConfig;
import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.utils.ImageDownloadUtil;
import cn.ipanel.ningxia.zw.constants.ZWConstants;

/** 获取政府信息公开目录下的新闻信息
 * @author qinmian
 *
 */
public class ArticleProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(ArticleProcessor.class);
	
	private ConcurrentHashMap<String, Date> dateInfoMap;
	
	private String currentHost;
	
	private String topicName;
	
	private String listRegex;
	
	private Date deadline;
	
	private boolean isFirst = true;
	
	private boolean isGKML = false;//是否政府信息公开目录
	
	private boolean isZFWJ = false;//是否自治区政府文件
	
	private boolean isXXGKZN = false;//是否自治区政府文件
	
	private int maxPage;//加载最大页码
	
	
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(5).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	public ArticleProcessor(String topicName,String listRegex,Date deadline,int maxPage) {
		this.topicName = topicName;
		this.listRegex = listRegex;
		this.deadline = deadline;
		this.maxPage = maxPage;
//		if(SysConfig.TOPIC_MAP.get(ZWConstants.ZFXXGKML).equals(topicName)){
//			isGKML = true ;
//			dateInfoMap = new ConcurrentHashMap<String, Date>();
//		}
		if(SysConfig.TOPIC_MAP.get(ZWConstants.ZFWJ).equals(topicName)){
			isZFWJ = true ;
			dateInfoMap = new ConcurrentHashMap<String, Date>();
		}
		if(SysConfig.TOPIC_MAP.get(ZWConstants.XXGKZN).equals(topicName)){
			isXXGKZN = true ;
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		if(!url.matches(listRegex)){
			//不是列表页
			try {
				Article article = getActicle(page);
				if(deadline == null){
					page.putField("article", article);					
				}else{
					if(article.getReleaseTime().getTime() >= deadline.getTime()){
						//满足时间要求的才会添加到数据库
						page.putField("article", article);					
					}else{
						page.setSkip(true);
					}
									
				}
			} catch (Exception e) {
				log.error("类别为:"+topicName+"-----解析新闻错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}else{
			try {
				if(isFirst){//第一次加载的话读取所有列表
					try {
						URI uri = new URI(url);
						String scheme = uri.getScheme();
						String host = uri.getHost();
						currentHost = scheme + "://" + host;
						setListlUrls(page);					
						
					} catch (Exception e) {
						log.error("解析首页获取所有列表页出现错误，错误URL为:------"+url, e);
						e.printStackTrace();
					}
					isFirst = false;
				}
				setArticelUrls(page);
				page.setSkip(true);
			} catch (Exception e) {
				log.error("类别为:"+topicName+"-----获取列表错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}
	}
	
	

	private Article getActicle(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		Element infoContent= document.getElementById("info_content");
		if(infoContent != null && infoContent.html() != null ){	
			String title = null;
			String subtitle = null;
			String source = null;
			Date releaseTime = null;
			Element titleElement = document.getElementById("info_title");
			if(document.getElementById("info_subtitle") != null){//政府文件之中没有相关属性
				title = titleElement.text();
				subtitle = document.getElementById("info_subtitle").text();
				source = document.getElementById("info_source").text();
				String timeStr = document.getElementById("info_released_dtime").text();
				releaseTime = DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss");
			}else if(isGKML || isZFWJ){
				//公开目录和政府文件
				Element subtitleElement = titleElement.getElementsByTag("p").get(0);
				subtitle = subtitleElement.text();
				subtitleElement.remove();
				title = titleElement.text();
				releaseTime = dateInfoMap.get(url);
			}else if(isXXGKZN){//信息公开指南
				title = titleElement.text();	
			}
			//获取内容详情
			Element contentElement = document.getElementById("info_content");
			//提取内容之中的图片，保存到本地，并改变内容之中图片的链接
			ImageDownloadUtil.changeImage(contentElement, url, topicName);
//			ZWProcessorImageUtil.changeZWImage(contentElement, url, topicName);
			String content = contentElement.html();
			Article article = new Article(title, subtitle, content, source, releaseTime, null, topicName);
			return article;
		}else if(document.getElementById("UCAP-CONTENT") != null ){
			Element articleContent = document.getElementsByClass("article").get(0);
			String title = articleContent.getElementsByTag("h1").get(0).text();
			
			Element dateElement = articleContent.getElementsByClass("pages-date").get(0);
			Elements children = dateElement.children();
			Element soueceElement = children.get(0);
			String text = soueceElement.text();
			String source = text.substring(text.lastIndexOf("：")+2, text.length());
			//移除子元素
			children.remove();
			//获取日期
			String timeStr = dateElement.text();
			Date releaseTime = DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm");
			
			Element contentElement = articleContent.getElementById("UCAP-CONTENT");
			ImageDownloadUtil.changeImage(contentElement, url, topicName);
//			ZWProcessorImageUtil.changeZWImage(contentElement, url, topicName);
			String content = contentElement.html();
			Article article = new Article(title, null, content, source, releaseTime, null, topicName);
			return article;
		}else{
			throw new Exception();
		}
	}
	
	private void setArticelUrls(Page page) throws Exception {
		
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取新闻链接列表
		Elements liElements = document.select(".list-con> ul> li");
		//获取ul标签下的li
//		Elements liElements = listElement.getElementsByTag("li");
		for(Element e : liElements ){
			//每个li标签下都有a标签,图片网站下有两个a标签，同取第一个即可
			Element aElement = e.select("a").get(0);
			String href = aElement.attr("href");
			if(isGKML || isZFWJ){//政府信息公开目录和自治区政府文件列表下有时间信息，直接获取判断
				Element dateElement = e.select(".date").get(0);
				String dateStr = dateElement.text();
				Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
				if(date != null){
					if(deadline != null && date.getTime() < deadline.getTime()){
						continue;//不符合日期要求的不加入队列						
					}
					//由于政府公开目录和自治区政府文件内容详情界面没有日期信息，从列表信息处保存
					if(!href.startsWith("http")){
						href = currentHost + href;
					}
					dateInfoMap.put(href, date);
				}
			}
			if(href.contains("weixin")){
				log.info("出现了微信链接-----—>URL="+href);
			}else{
				page.addTargetRequest(href);						
			}
		}
	}
	
	
	private void setListlUrls(Page page) throws Exception {	
		Document document = page.getHtml().getDocument();
		//获取页码信息
		Element pageElement = document.select(".pageing").get(0);
		String totalNumStr ;
		if(isGKML){//获取信息公开目录下界面下的总页数
			//获取有总页码数的节点
			Element totalElement = pageElement.select("span").get(2);
			String text = totalElement.text();
			totalNumStr = text.substring(1, text.length()-1);
		}else{
			//获取有总页码数的节点
			Element totalElement = pageElement.select("span").get(1);
			String text = totalElement.text();
			//截取字符串获取总页码数
			totalNumStr = text.substring(5, text.length()-1);
		}
		Integer totalNum = Integer.valueOf(totalNumStr);
		String firstPageUrl = page.getRequest().getUrl();
		String basListUrl = firstPageUrl.substring(0, firstPageUrl.length()-1);
		//获取所有页码url
		for(int i = 2 ; i <= totalNum && i <= maxPage ; i++){
			page.addTargetRequest(basListUrl+i);
		}
	}
	
}
