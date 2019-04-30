package cn.ipanel.ningxia.zw.processor;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.utils.ImageDownloadUtil;

/** 获取央网动态下面的数据
 * @author qinmian
 *
 */
public class YWDTArticleProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(YWDTArticleProcessor.class);
	private String topicName;
	private String listRegex;
	private Date deadline;
	private int maxPage;
	private boolean isFirst = true;
	
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(5).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	public YWDTArticleProcessor(String topicName,String listRegex,Date deadline,int maxPage) {
		this.topicName = topicName;
		this.listRegex = listRegex;
		this.deadline = deadline;
		this.maxPage = maxPage;
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
				Article article = getYWDTActicle(page);
				if(deadline == null){
					page.putField("article", article);					
				}else{
					if(article.getReleaseTime().getTime() > deadline.getTime()){
						//满足时间要求的才会添加到数据库
						page.putField("article", article);					
					}else{
						page.setSkip(true);
					}	
				}
			} catch (Exception e) {
				log.error(topicName+"------>解析错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}else{
			try {
				if(isFirst){//第一次加载的话读取所有列表
					try {
						setYWDTListlUrls(page);
					} catch (Exception e) {
						log.error(topicName+"----->获取所有列表页出现错误，错误URL为:------"+url, e);
						e.printStackTrace();
					}
					isFirst = false;
				}
				setYWDTArticelUrls(page);
				page.setSkip(true);
			} catch (Exception e) {
				log.error(topicName+"------>获取内容URL错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}
	}
	
	private Article getYWDTActicle(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		Elements infoContents = document.select(".article");
		Elements wrapElemets = document.select(".wrap");
		if(infoContents != null && infoContents.size() > 0){	
			String title = infoContents.get(0).select("h1").get(0).text();
			
			Element dateElement = infoContents.get(0).select(".pages-date").get(0);
			Elements children = dateElement.children();
			Element soueceElement = children.get(0);
			String text = soueceElement.text();
			String source = text.substring(text.lastIndexOf("：")+2, text.length());
			//移除子元素
			children.remove();
			//获取日期
			String timeStr = dateElement.text();
			Date releaseTime = DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm");
			
			Element contentElement = document.getElementById("UCAP-CONTENT");
			//移除其中的视频链接
			Elements videoElements = contentElement.select("embed");
			videoElements.remove();
			ImageDownloadUtil.changeImage(contentElement, url, topicName);
			String content = contentElement.html();
			Article article = new Article(title, null, content, source, releaseTime, null, topicName);
			return article;
		}else if(wrapElemets != null && wrapElemets.size() > 0){//链接为/zhengce/content
			Element wrapClassElement = wrapElemets.get(0);
			Elements elements = wrapClassElement.children();
			//信息table
			Element infoElement = elements.get(0);
			Elements trElements = infoElement.child(0).select("table").get(0).select("tr");
			//获取新闻发布来源
			String source = trElements.get(1).getElementsByTag("td").get(1).text();
			//获取新闻标题
			String title = trElements.get(2).getElementsByTag("td").get(1).text();
			//获取发布日期
			String dateStr = trElements.get(3).getElementsByTag("td").get(3).text();
			Date releaseDate = DateUtils.parseDate(dateStr, "yyyy年MM月dd日");
			//获取正文
			Element contentElement = elements.get(2);
			//获取正文的table
			Element contentTableElement;
			//目前发现两个样式
			try {
				contentTableElement = contentElement.child(0).child(0).child(0).child(0).child(0).select("table").get(0);
			} catch (Exception e) {
				contentTableElement = contentElement.child(0).child(0).child(0).select("table").get(0);
			}
			ImageDownloadUtil.changeImage(contentElement, url, topicName);
			String content = contentTableElement.html();
			Article article = new Article(title, null, content, source, releaseDate, null, topicName);
			return article;
		}
		throw new Exception("解析错误");
	}
	
	private void setYWDTArticelUrls(Page page) throws Exception {
		
		Document document = page.getHtml().getDocument();
		//获取新闻链接列表
		Element listElement = document.select(".listTxt").get(0);
		//获取ul标签下的li
		Elements liElements = listElement.select("li");
		for(Element e : liElements ){
			//每个li标签下都有a标签,图片网站下有两个a标签，同取第一个即可
			Element aElement = e.select("a").get(0);
			String href = aElement.attr("href");
			//改为绝对路径加入列表
			if(deadline != null ){
				//央网动态列表页有日期信息，如果设置了截至时间，则不添加不满足要求的链接
				Element dateElement = e.select(".date").get(0);
				String dateStr = dateElement.text();
				Date date = DateUtils.parseDate(dateStr, "yyyy.MM.dd");
				if(date != null && date.getTime() < deadline.getTime()){
					continue;
				}
				
			}	
			page.addTargetRequest(href);
		}
	}
	
	
	private void setYWDTListlUrls(Page page) throws Exception {	
		Document document = page.getHtml().getDocument();
		//获取页码信息
		Element pageElement = document.getElementById("toPage");
		//获取有总页码数的节点
		Element totalElement = pageElement.children().get(1);
		String text = totalElement.text();
		//截取字符串获取总页码数
		String totalNumStr = text.substring(1, text.length()-1);
		Integer totalNum = Integer.valueOf(totalNumStr);
		String firstPageUrl = page.getRequest().getUrl();
		String basListUrl = firstPageUrl.substring(0, firstPageUrl.lastIndexOf("/")+1);
		//获取所有页码url
		for(int i = 1 ; i < totalNum && i < maxPage; i++){
			page.addTargetRequest(basListUrl+i+".htm");
		}
	}

}
