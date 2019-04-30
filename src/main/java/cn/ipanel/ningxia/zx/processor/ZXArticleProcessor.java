package cn.ipanel.ningxia.zx.processor;

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

public class ZXArticleProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(ZXArticleProcessor.class);
	private String topicName;
	private String meeting;
	private String listRegex;
	private Date deadline;
	private boolean isFirst = true;
	private int maxPage ; //检索列表的最大页码数	
	
	private Site site = Site.me().setTimeOut(20000).setRetryTimes(5).setSleepTime(15000);

	public ZXArticleProcessor(String topicName,String listRegex,Date deadline,int maxPage) {
		this(topicName, listRegex, deadline, maxPage,null);
	}
	
	public ZXArticleProcessor(String topicName,String listRegex,Date deadline,
						int maxPage,String meeting) {
		this.topicName = topicName;
		this.listRegex = listRegex;
		this.deadline = deadline;
		this.maxPage = maxPage;
		this.meeting = meeting;
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
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		//获取标题
		Element titleElement = document.select(".listchfont20h25").get(0);
		String title = titleElement.text();
		//获取日期
		Date releaseTime = null;
		Elements dateElements = document.select(".listblackf12h25");
		//匹配日期正则
		Pattern p = Pattern.compile("\\d+-\\d+-\\d+");
		Matcher m;
		if(dateElements != null && dateElements.size() > 0){
			String text = dateElements.get(0).text();
			m = p.matcher(text);
		}else{
			String text = document.text();
			m = p.matcher(text);
		}
		while(m.find()){
        	String dateStr = m.group();
        	releaseTime = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
        	break;
        }	
		//获取新闻来源
		Element sourceElements = document.select(".listblackf12h22").get(0);
		String sourceText = sourceElements.text().trim();
		String source = sourceText.substring(5).trim();
		//获取正文 TRS_PreAppend
		Elements contentElements = document.select(".TRS_Editor");
		Element contentElement;
		if(contentElements != null && contentElements.size() > 0 ){
			contentElement = contentElements.get(0);
		}else{
			contentElement = document.select(".TRS_PreAppend").get(0);
		}
//		ZXProcessorImageUtil.changeZXImage(contentElement, url, topicName);
		ImageDownloadUtil.changeImage(contentElement, url, topicName);
		String content = contentElement.html();
		Article article = new Article(title, null, content, source, releaseTime, meeting, topicName);
		return article;
	}
	
	private void setArticelUrls(Page page) throws Exception {
		boolean isImageNews = false;//是否图片新闻
		Document document = page.getHtml().getDocument();
		//获取所有链接列表
		Elements tdElements = document.select(".listblackf14h25");
		if(tdElements == null || tdElements.size() == 0){
			//图片列表时获取链接
			tdElements = document.select(".listsbf12h25");
			isImageNews = true;
		}
		//获取每个链接
		for(Element e : tdElements ){
			//每个td标签下都有a标签
			Element aElement = e.select("a").get(0);
			String href = aElement.attr("href");
			//改为绝对路径加入列表
			if(deadline != null && !isImageNews){
				Element dateElement = e.nextElementSibling();
				String dateStr = dateElement.text();
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
