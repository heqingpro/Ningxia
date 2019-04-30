package cn.ipanel.ningxia.rd.processor;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.rd.constants.RDConstants;
import cn.ipanel.ningxia.utils.ImageDownloadUtil;

/** 用于抓取人大专题下面跟其他栏目不同的信息
 * @author qinmian
 *
 */
public class RDArticleProcessorForRDZT implements PageProcessor{

	private static final Logger log = Logger.getLogger(RDArticleProcessorForRDZT.class);
	private String topicName;//所属专题
	private String listRegex;//内容列表页url正则
	private String meetinglistRegex;//会议列表页url正则
	private Date deadline;//抓取数据截止时间
	private boolean isMeetingFirst = true;//是否首次访问会议列表
	//检索列表的最大页码数
	private int maxPage = SpiderConfig.IS_FIRST_TIME ? 
							RDConstants.RD_COMMON_MAX_PAGE : SpiderConfig.NOT_FIRST_TIME_PAGE; 
	//内容列表后面的字符串，比如：http://www.nxrd.gov.cn/rdzt/qgrdhnxdbt/wc/scyw/index.html，该字段表示的为scyw
	private String[] listUrlEndStrs ;
	
	private Site site = Site.me().setTimeOut(20000).setRetryTimes(5).setSleepTime(20000);

	public RDArticleProcessorForRDZT(String topicName,String listRegex,String meetinglistRegex,
										String[] listUrlEndStrs,Date deadline) {
		this.topicName = topicName;
		this.listRegex = listRegex;
		this.deadline = deadline;
		this.meetinglistRegex = meetinglistRegex;
		this.listUrlEndStrs = listUrlEndStrs;
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		if(!url.matches(listRegex) && !url.matches(meetinglistRegex)){
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
			}
		}else{
			try {
				if(url.matches(meetinglistRegex)){
					if (isMeetingFirst) {
						setListUrls(page);
						isMeetingFirst = false;
					}
					setMeetingUrls(page);
				}else{
					if(url.contains("index.html")){//第一次加载的话读取列表页						
						setListUrls(page);				
					}
					setArticelUrls(page);
				}
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
		//获取正文 
		Elements contentElements = document.select(".TRS_Editor");
		Element contentElement;
		if(contentElements != null && contentElements.size() > 0 ){
			contentElement = contentElements.get(0);
		}else{
			contentElement = document.select("TRS_PreAppend").get(0);
		}
		ImageDownloadUtil.changeImage(contentElement, url, topicName);
		String content = contentElement.html();
		//获取contentElement所在的tr节点
		Element trContentElement = contentElement.parent().parent();
		//获取日期
		Date releaseTime = null;
		//获取时间所在的tr，
		Element trDateElement = trContentElement.previousElementSibling();
		//匹配日期正则
		Pattern p = Pattern.compile("\\d+-\\d+-\\d+");
		Matcher m;
		String text = trDateElement.text();
		m = p.matcher(text);
		while(m.find()){
			String dateStr = m.group();
			releaseTime = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
			break;
		}	
		//获取副标题
		Element trSubtitleElement = trDateElement.previousElementSibling().previousElementSibling();
		String subTitle = trSubtitleElement.text();
		
		//获取标题
		Element trTitleElement = trSubtitleElement.previousElementSibling();
		String title = trTitleElement.text();
		
		//获取新闻来源,来源在所有tr节点的最后一个
		//获取所有的tr节点
		Elements trElements = trTitleElement.parent().children();
		Element sourceElement = trElements.get(trElements.size()-1).select(".listblackf12h25").first();
		String sourceText = sourceElement.text().trim();
		//在来源信息之中会存在夹带日期，删除这些
		m = p.matcher(sourceText);
		while(m.find()){
			String dateStr = m.group();
			sourceText = sourceText.replaceAll(dateStr, "");
			break;
		}
		String source = sourceText.substring(3).trim();
		//获取会议名称
		Elements meetingElements = document.getElementsByClass("CurrChnlCls");
		//在面包屑导航的倒数第二个节点
		Element meetingElement = meetingElements.get(meetingElements.size() - 2 );
		String meeting = meetingElement.text();
		Article article = new Article(title, subTitle, content, source, releaseTime, meeting, topicName);
		return article;
	}
	
	private void setArticelUrls(Page page) throws Exception {
		setUrlFromList(page, true);
	}
	
	private void setMeetingUrls(Page page) throws Exception {
		setUrlFromList(page, false);
		
	}
	
	/** 从列表页获取链接
	 * @param page
	 * @param isArticleList 是新闻列表或者会议列表
	 * @throws Exception 
	 */
	private void setUrlFromList(Page page , boolean isArticleList) throws Exception{
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取所有链接列表
		Elements tdElements = document.getElementsByClass("listblackf14h25");
		if(tdElements == null || tdElements.size() == 0){
			throw new Exception("该列表下无对应链接");
		}
		//获取每个链接
		StringBuilder sb ;
		for(Element e : tdElements ){
			//获取a标签
			Elements aElements = e.getElementsByTag("a");
			if(aElements == null || aElements.size() < 1){
				//有可能其中没有a标签
				continue;
			}
			Element aElement = aElements.get(0);
			String href = aElement.attr("href");
			//改为绝对路径加入列表
			if(deadline != null ){
				Element dateElement = e.nextElementSibling();
				String dateStr = dateElement.text();
				Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
				if(date != null && date.getTime() < deadline.getTime()){
					continue;
				}
				
			}
			if(isArticleList){
				page.addTargetRequest(href);
			}else{
				for(String endUrl : listUrlEndStrs){
					sb = new StringBuilder();
					sb.append(href).append(endUrl).append("/index.html");
					page.addTargetRequest(sb.toString());									
				}
			}
		}
	}
	
	
	private void setListUrls(Page page) throws Exception {	
		Integer totalPage = 0;	
		String html = page.getHtml().toString();
		Pattern p = Pattern.compile("createPageHTML\\(\\d+.*+");
        Matcher m = p.matcher(html);
        while(m.find()){
        	String text = m.group();
        	String pageStr = text.substring(15, text.indexOf(","));
        	totalPage = Integer.valueOf(pageStr);
        	break;
        }
        String firstPageUrl = page.getRequest().getUrl();
		String basListUrl = firstPageUrl.substring(0, firstPageUrl.lastIndexOf("."));
		//获取所有页码url	
		for(int i = 1 ; i < totalPage && i <= maxPage; i++){
			page.addTargetRequest(basListUrl+"_"+i+".html");
		}
	}
}
