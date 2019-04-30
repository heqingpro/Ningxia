package cn.ipanel.ningxia.rd.processor;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.utils.ImageDownloadUtil;

public class RDArticleProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(RDArticleProcessor.class);
	private String topicName;
	private String meeting;
	private String listRegex;
	private Date deadline;
	private boolean isFirst = true;
	private int maxPage; //检索列表的最大页码数
	
	private Site site = Site.me().setTimeOut(20000).setRetryTimes(5).setSleepTime(20000);

	public RDArticleProcessor(String topicName,String listRegex,Date deadline,int maxPage) {
		this(topicName, listRegex, deadline, null,maxPage);
	}
	
	public RDArticleProcessor(String topicName,String listRegex,Date deadline,
							String meeting,int maxPage) {
		this.topicName = topicName;
		this.listRegex = listRegex;
		this.deadline = deadline;
		this.meeting = meeting;
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
				if(isFirst){//第一次加载的话读取所有列表
					try {
						setListlUrls(page);					
						
					} catch (Exception e) {
						log.error("解析首页获取所有列表页出现错误，错误URL为:------"+url, e);
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
		Document document = page.getHtml().getDocument();
		String url = page.getRequest().getUrl();
		//获取正文 
		Elements contentElements = document.select(".TRS_Editor");
		Element contentElement;
		if(contentElements != null && contentElements.size() > 0 ){
			contentElement = contentElements.get(0);
		}else{
			contentElement = document.select(".TRS_PreAppend").get(0);
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
		Article article = new Article(title, subTitle, content, source, releaseTime, meeting, topicName);
		return article;
	}
	
	private void setArticelUrls(Page page) throws Exception {
		
		Document document = page.getHtml().getDocument();
		Elements dateElements = document.select(".listblackfont12h18");
		//获取所有链接列表
		if(dateElements == null || dateElements.size() == 0){
			dateElements = document.select(".listblackf12h25");
			if(dateElements == null || dateElements.size() == 0){
				throw new Exception("该列表页无对应数据");				
			}
		}
		//获取每个链接
		for(Element e : dateElements ){
			//获取a标签
			Elements aElements = e.previousElementSibling().getElementsByTag("a");
			if(aElements == null || aElements.size() < 1){
				//有可能其中没有a标签
				continue;
			}
			Element aElement = aElements.get(0);
			String href = aElement.attr("href");
			//改为绝对路径加入列表
			if(deadline != null ){
				String dateStr = null;
				try {
					dateStr = e.text();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
				if(date != null && date.getTime() < deadline.getTime()){
					continue;
				}
				
			}
			page.addTargetRequest(href);
		}
	}
	
	
	private void setListlUrls(Page page) throws Exception {	
		String html = page.getHtml().toString();
		Pattern p = Pattern.compile("createPageHTML\\(\\d+.*+");
        Matcher m = p.matcher(html);
        Integer totalPage = 0 ;
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
