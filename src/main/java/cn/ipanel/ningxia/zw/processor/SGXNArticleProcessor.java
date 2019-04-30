package cn.ipanel.ningxia.zw.processor;

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
import cn.ipanel.ningxia.utils.ImageDownloadUtil;

/** 获取实干兴宁界面的数据
 * @author qinmian
 *
 */
public class SGXNArticleProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(SGXNArticleProcessor.class);
	private String topicName;
	private String listRegex;
	private Date deadline;
	private boolean isFirst = true;
	private int maxPage ;	
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(3).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	public SGXNArticleProcessor(String topicName,String listRegex,Date deadline,int maxPage) {
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
				Article article = getActicle(page);
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
				log.error("解析新闻错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}else{
			try {
				if(isFirst){//第一次加载的话读取所有列表
					try {
						setListlUrls(page);
					} catch (Exception e) {
						log.error("实干兴宁首页获取所有列表页出现错误，错误URL为:------"+url, e);
						e.printStackTrace();
					}
					isFirst = false;
				}
				setArticelUrls(page);
				page.setSkip(true);
			} catch (Exception e) {
				log.error("获取新闻列表错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}
	}
	
	/** 抓取实干兴宁新闻
	 * @param page
	 * @return
	 * @throws Exception
	 */
	private Article getActicle(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		Elements dbtElements = document.select(".dbt110");
		String title ;
		if(dbtElements != null && dbtElements.size() > 0){
			title = dbtElements.get(0).text();
		}else{
			title = document.select(".zwbt").get(0).text();
		}
		String str = document.select(".listblackf14h25").get(0).text();
		int index = str.indexOf("来源:");
		String timeStr = str.substring(0,index-3);
		Date releaseTime = DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd");
		String source = str.substring(index+3,str.length());
		Element contentElement = document.select(".TRS_Editor").get(0);
//		ZWProcessorImageUtil.changeSpecialZWImage(contentElement, url, topicName);
		ImageDownloadUtil.changeImage(contentElement, url, topicName);
		String content = contentElement.html();
		Article article = new Article(title, null, content, source, releaseTime, null, topicName);
		return article;
	}
	
	private void setArticelUrls(Page page) throws Exception {
		
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取div
		Elements div = document.select(".col-md-8");
		Element listElement = div.get(0);
		//获取ul
		Elements ulElements = listElement.select("ul");	
		for(Element e : ulElements ){
			//获取每个ul的li
			Element liElement = e.select("li").get(0);
			//每个li标签下都有a标签,图片网站下有两个a标签，同取第一个即可
			Element aElement = liElement.select("a").get(0);
			String href = aElement.attr("href");
			page.addTargetRequest(href);
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
