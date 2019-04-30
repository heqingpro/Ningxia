package cn.ipanel.ningxia.rd.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import cn.ipanel.ningxia.utils.URIUtil;

/** 用于抓取人大专题下面 机关主题教育实践活动的内容
 * @author qinmian
 *
 */
public class RDArticleProcessorForSJHD implements PageProcessor{

	private static final Logger log = Logger.getLogger(RDArticleProcessorForSJHD.class);
	private String topicName = "jgztjysjhd";//所属专题
	private String pageRegex = "http://www\\.nxrd\\.gov\\.cn/rdzt/\\w+/?(\\w+)?/?(index(_\\d+)?\\.html)?";//页码页正则表达式
	private String contentRegex = "http://www\\.nxrd\\.gov\\.cn/rd\\w+/\\w+/\\w+/(\\w+/)?((?!index).+)\\.html";//内容页url正则
	private Date deadline;//抓取数据截止时间
	private int maxPage ; //检索列表的最大页码数
	private Site site = Site.me().setTimeOut(20000).setRetryTimes(5).setSleepTime(10000);

	public RDArticleProcessorForSJHD(String topicName,Date deadline) {
		this.topicName = topicName;
		this.deadline = deadline;
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		if(url.matches(contentRegex)){//如果是内容页
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
				if(isPageList(page)){//如果是列表页
					if(isFirstPage(url)){//如果是首页的话，加载页码的url
						setPageUrls(page);
					}
					setUrlFromList(page);
				}else{//展示页
					List<String> moreUrls = findMoreUrls(page);
					if(moreUrls != null && moreUrls.size() > 0){
						page.addTargetRequests(moreUrls);
					}else{
						//如果没有更多链接，则从展示页之中获取新闻的链接
						setArticleUrlsFormShowPage(page);
					}
				}
				page.setSkip(true);
			} catch (Exception e) {
				log.error("类别为:"+topicName+"-----解析李彪错误,错误URL为:---------"+url,e);
			}
		}
	}
	

	/** 从展示页之中获取新闻链接
	 * @param page
	 * @throws Exception 
	 */
	private void setArticleUrlsFormShowPage(Page page) throws Exception {
		Document document = page.getHtml().getDocument();
		Elements elements = document.select(".listblackf14h25[align=left]");
		for(Element e : elements){
			Elements aElements = e.select("a");
			if(aElements != null && aElements.size() > 0){
				String href = URIUtil.getAbsoluteUrl(aElements.get(0).attr("href"),page.getRequest().getUrl());
				page.addTargetRequest(href);
			}
		}
		//查找有没有图片新闻
		Elements imgNewsElements = document.select(".ImgNews");
		if(imgNewsElements != null && imgNewsElements.size() > 0){
			//有的话进行获取
			for(Element e : imgNewsElements){
				Elements aElenmets = e.select("a");
				if(aElenmets != null && aElenmets.size() > 0){
					String href = URIUtil.getAbsoluteUrl(aElenmets.get(0).attr("href"),page.getRequest().getUrl());
					page.addTargetRequest(href);
				}
			}
		}
	}

	/** 查找更多链接
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	private List<String> findMoreUrls(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		//map标签里面有多余的链接，删除
		Elements elements = document.select("map");
		elements.remove();
		Elements aElements = document.select("a");
		List<String> urls = new ArrayList<String>();
		for(Element e : aElements){
			String href = e.attr("href");
			//以/结尾的路径在转换相对路径是才能成功
			if(!href.endsWith(".html") && !href.endsWith("/")){
				href += "/";
			}
			//将相对路径改为绝对路径
			String absoluteUriStr = URIUtil.getAbsoluteUrl(href, url);
			if(absoluteUriStr.matches(pageRegex) && !absoluteUriStr.equals(url)){
				urls.add(absoluteUriStr);
			}
		}
		return urls;
	}

	/** 判断是否为列表页
	 * @param page
	 * @return
	 */
	private boolean isPageList(Page page) {
		String html = page.getHtml().toString();
		//在列表页网页源码之中会有产生页码的js代码，作为判断依据
		Pattern p = Pattern.compile("createPageHTML\\(\\d+.*+");
        Matcher m = p.matcher(html);
        while(m.find()){
        	return true;
        }
		return false;
	}

	/** 判断是否为列表页首页
	 * @param url
	 * @return
	 */
	private boolean isFirstPage(String url) {
		String notFirstRegex = "http://www\\.nxrd\\.gov\\.cn/rdzt/.+/index_\\d+\\.html";//不是首页时候的正则表达式
		if(url.matches(notFirstRegex)){
			return false;
		}
		return true;
	}

	private Article getActicle(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		//获取正文 
		Elements contentElements = document.getElementsByClass("TRS_Editor");
		Element contentElement;
		if(contentElements != null && contentElements.size() > 0 ){
			contentElement = contentElements.get(0);
		}else{
			contentElement = document.getElementsByClass("TRS_PreAppend").get(0);
		}
		//更换下载正文之中的图片
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
		Article article = new Article(title, subTitle, content, source, releaseTime, null, topicName);
		return article;
	}
	
	/** 从列表页获取链接
	 * @param page
	 * @param isArticleList 是新闻列表或者会议列表
	 * @throws Exception 
	 */
	private void setUrlFromList(Page page) throws Exception{
		Document document = page.getHtml().getDocument();
		//获取所有链接列表
		Elements tdElements = document.getElementsByClass("listblackf14h25");
		if(tdElements == null || tdElements.size() == 0){
			//图片列表时获取链接
//			tdElements = document.getElementsByClass("listsbf12h25");
		}
		//获取每个链接
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
			href = URIUtil.getAbsoluteUrl(href, page.getRequest().getUrl());
			page.addTargetRequest(href);
		}
	}
	
	
	private void setPageUrls(Page page) throws Exception {	
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
        if(!firstPageUrl.endsWith(".html")){
        	firstPageUrl = firstPageUrl + "/index.html";
        }
		String basListUrl = firstPageUrl.substring(0, firstPageUrl.lastIndexOf("."));
		//获取所有页码url	
		for(int i = 1 ; i < totalPage && i <= maxPage; i++){
			page.addTargetRequest(basListUrl+"_"+i+".html");
		}
	}

}
