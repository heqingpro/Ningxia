package cn.ipanel.ningxia.zw.processor;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
import cn.ipanel.ningxia.utils.URIUtil;
import cn.ipanel.ningxia.zw.constants.ZWConstants;

/** 获取政府信息公开目录下的新闻信息
 * @author qinmian
 *
 */
public class ZWArticleProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(ZWArticleProcessor.class);
	private ConcurrentHashMap<String, Date> dateInfoMap;
//	private String currentHost;
	private String topicName;
	private String listRegex;
	private Date deadline;
	private boolean isFirst = true;
	private boolean isGKML = false;//是否政府信息公开目录
	private boolean isZFWJ = false;//是否自治区政府文件
	private boolean isXXGKZN = false;//是否自治区政府文件
	
	private int maxPage;//加载最大页码
	
	
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(5).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	public ZWArticleProcessor(String topicName,String listRegex,Date deadline,int maxPage) {
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
		//获取ul标签下的li
		Elements liElements = document.select(".list-con> ul> li");
		for(Element e : liElements ){
			Elements dateElements = e.select(".date");
			if(dateElements == null || dateElements.size() < 1){
				continue;
			}
			String dateStr = dateElements.get(0).text();
			Date date = null;
			if(dateStr.contains("-") && dateStr.length() > 7){
				date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
				if(date.before(deadline)){//日期不和要求
					continue;
				}
			}else if(StringUtils.isBlank(dateStr)){//如果列表没有时间信息，也直接结束循环
				continue;
			}
			//每个li标签下都有a标签,图片网站下有两个a标签，同取第一个即可
			Element aElement = e.select("a").get(0);
			String href = aElement.attr("href");
			//可能之中存在相对路径，替换为绝对路径
			href = URIUtil.getAbsoluteUrl(href, page.getRequest().getUrl());
			
			//从链接上获取日期信息
			try {
				String substring = href.substring(href.lastIndexOf("/")+1);
				substring = substring.substring(1, substring.indexOf("_"));
				if(substring.length() == 8){
					//获取到的日期格式是yyyyMMdd
					date = DateUtils.parseDate(substring, "yyyyMMdd");
					if(date.before(deadline)){//日期不和要求
						continue;
					}
				}
			} catch (Exception e1) {
				//
			}
			if(isGKML || isZFWJ){
				dateInfoMap.put(href, date);
			}
			if(href.contains("weixin")){
				log.info("出现了微信链接-----—>URL="+href);
			}else{
				page.addTargetRequest(href);						
			}
		}
	}
	
	
	private void setListlUrls(Page page) throws Exception {	
		String html = page.getHtml().toString();
		Pattern p = Pattern.compile("createPageHTML\\(\\d+.*+");
        Matcher m = p.matcher(html);
        Integer totalPage = 11;
        while(m.find()){
        	String text = m.group();
        	String pageStr = text.substring(15, text.indexOf(","));
        	totalPage = Integer.valueOf(pageStr);
        }	
        String firstPageUrl = page.getRequest().getUrl();
		String basListUrl = firstPageUrl.substring(0, firstPageUrl.lastIndexOf("."));
		//获取所有页码url	
		for(int i = 1 ; i < totalPage && i < maxPage; i++){
			page.addTargetRequest(basListUrl+"_"+i+".html");
		}
	}
	
}
