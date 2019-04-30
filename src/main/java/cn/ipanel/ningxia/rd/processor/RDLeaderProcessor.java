package cn.ipanel.ningxia.rd.processor;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.ipanel.ningxia.entity.Leader;
import cn.ipanel.ningxia.utils.ImageDownloadUtil;

public class RDLeaderProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(RDLeaderProcessor.class);
	private String savePath = "download";
	private String belong = "rd";//领导所属,人大网下为rd
	private boolean isFirst = true;
	private ConcurrentHashMap<String, Leader> leaderMap = new ConcurrentHashMap<String, Leader>();
	
	private Site site = Site.me().setTimeOut(20000).setRetryTimes(5).setSleepTime(5000);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		if(isFirst){
			try {
				setLeaderUrls(page);
				page.setSkip(true);
				isFirst = false;
			} catch (Exception e) {
				log.error("类别为:人大领导-----解析错误,错误URL为:---------"+url,e);
			}
		}else{
			try {
				Leader leader = getLeader(page);
				page.putField("leader", leader);
			} catch (Exception e) {
				log.error("类别为:人大领导-----获取常委会领导列表错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}
	}
	
	
	private Leader getLeader(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		Element contentElement = document.select(".TRS_Editor").get(0);
		ImageDownloadUtil.changeImage(contentElement, url, savePath);
		Element headImageElement = contentElement.select("img").get(0);
		//获取领导头像信息
		String headImageUrl = headImageElement.attr("src");
		//获取之前存放的leader
		Leader leader = leaderMap.get(url);
		
		leader.setHeadImage(headImageUrl);	
		headImageElement.remove();
		String resume = contentElement.html();
		String regex = "<font.*?>[ *| *| *|　*|\\s*]*</font>";
		resume = resume.replace(regex, "");
		leader.setResume(resume);
		return leader;
	}

	private void setLeaderUrls(Page page) throws Exception {
		URL url = new URL(page.getRequest().getUrl());
		String base = url.getProtocol()+"://"+url.getHost();
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		Elements trLeaderInfoElements = document.select(".bk> tbody> tr> td> table> tbody> tr");
		Leader leader;
		for(Element element : trLeaderInfoElements){
			//获取职务
			Element dutiesElement = element.select(".listblackf14h25").get(0);
			String dutiesStr = dutiesElement.text();
			//删除空格，一些为全角空格
			dutiesStr = dutiesStr.replaceAll("[ *| *| *|　*|\\s*]*", "");
			String duties = dutiesStr;
			Elements leaderInfoElements = element.select(".listblackf12h25");
			for(Element e : leaderInfoElements){
				String name = e.text().replaceAll("[ *| *| *|　*|\\s*]*", "");
				leader = new Leader(name, duties, null, null, null,belong);
				Elements aElements = e.getElementsByTag("a");
				if(aElements != null && aElements.size() > 0 ){
					String href = aElements.get(0).attr("href");
					if(href.startsWith("http")){
						leaderMap.put(href, leader);
					}else{
						leaderMap.put(base+href, leader);						
					}
					page.addTargetRequest(href);
				}
			}
		}
	}
	
}
