package cn.ipanel.ningxia.zw.processor;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
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
import cn.ipanel.ningxia.entity.ImageArticle;
import cn.ipanel.ningxia.utils.ImageDownloadUtil;
import cn.ipanel.ningxia.utils.URIUtil;

/** 获取塞上江南新的新闻数据
 * @author qinmian
 *
 */
public class ImageArticleProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(ImageArticleProcessor.class);
	private String topicName;
	private String listRegex;
	private boolean isFirst = true;
	private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
	
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(5).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	public ImageArticleProcessor(String topicName,String listRegex) {
		this.topicName = topicName;
		this.listRegex = listRegex;
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
				ImageArticle imageArticle = getImageActicle(page);
				page.putField("imageArticle", imageArticle);					
				
			} catch (Exception e) {
				log.error("类别为:"+topicName+"-----解析塞上江南错误,错误URL为:---------"+url,e);
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
	
	private ImageArticle getImageActicle(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		Element infoContent= document.getElementById("info_content");
		if(infoContent != null && infoContent.html() != null ){	
			String img = concurrentHashMap.get(url);
			Element titleElement = document.getElementById("info_title");
			String title = titleElement.text();
			String source = document.getElementById("info_source").text();
			String timeStr = document.getElementById("info_released_dtime").text();
			Date releaseTime = DateUtils.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss");
			//获取内容详情
			Element contentElement = document.getElementById("info_content");
			//提取内容之中的图片，保存到本地，并改变内容之中图片的链接
			ImageDownloadUtil.changeImage(contentElement, url, topicName);
			String content = contentElement.html();
			ImageArticle imageArticle = new ImageArticle(title, content, source, releaseTime, topicName, img);
			return imageArticle;
		}else{
			throw new Exception();
		}
	}
	
	private void setArticelUrls(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取新闻链接列表
		Elements liElements = document.select(".list-con> ul> li");
		for(Element e : liElements ){
			//每个li标签下都有a标签,图片网站下有两个a标签，同取第一个即可
			Element aElement = e.select("a").get(0);
			String href = aElement.attr("href");
			href = URIUtil.getAbsoluteUrl(href,url);
			Elements imgElements = aElement.select("img");
			if(imgElements != null && imgElements.size() > 0){
				String imageUrl = imgElements.get(0).attr("src");
				imageUrl = ImageDownloadUtil.downloadImage(url, imageUrl);
				concurrentHashMap.put(href, imageUrl);
			}
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
		for(int i = 1 ; i < totalPage; i++){
			page.addTargetRequest(basListUrl+"_"+i+".html");
		}
	}
	
}
