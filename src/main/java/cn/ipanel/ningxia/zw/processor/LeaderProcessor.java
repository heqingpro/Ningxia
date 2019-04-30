package cn.ipanel.ningxia.zw.processor;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.entity.Leader;
import cn.ipanel.ningxia.utils.ImageDownloadUtil;
import cn.ipanel.ningxia.utils.URIUtil;

public class LeaderProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(LeaderProcessor.class);
	private boolean isFirst = true;
	private String belong = "zw";//领导所属，政务网为zw
	
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(3).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	private ConcurrentHashMap<String, String> leaderHeadImgMap 
							= new ConcurrentHashMap<String, String>();
	
	public LeaderProcessor() {	
	}
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		if(isFirst){
			//首次访问加载领导列表
			try {
				setLeaderUrls(page);
			} catch (Exception e) {
				log.error("获取领导详情界面URL错误,错误URL为:---------"+url);
				e.printStackTrace();
			}
			isFirst = false;
			page.setSkip(true);
		}else{
			try {
				Leader leader = getLeader(page);
				page.putField("leader", leader);							
			} catch (Exception e) {
				log.error("解析领导详情界面错误,错误URL为:---------"+url);
				e.printStackTrace();
			}
		}
	}
	
	private Leader getLeader(Page page) throws Exception {
		String url = page.getRequest().getUrl();
		Document document = page.getHtml().getDocument();
		boolean isMSZ = false;//是否是秘书长
		Elements leftBoxElements = document.select(".leftBox");
		Element headElement ;
		Elements jobMainElements;
		if(leftBoxElements != null && leftBoxElements.size() > 0){
			Element leftBoxElement = leftBoxElements.get(0);
			headElement = leftBoxElement.select("div").get(0);
			
			jobMainElements = leftBoxElement.select(".job_main");
		}else{//秘书长
			headElement = document.select(".msz_pic").get(0);
			//获取头像信息
			jobMainElements = document.select(".job_main");
			isMSZ = true;
		}
		//获取头像信息
		//从缓存map之中取
//		Element imgElement = headElement.getElementsByTag("img").get(0);
//		String imgUrl = imgElement.attr("src");
		String imgUrl = leaderHeadImgMap.get(url);
		imgUrl = ImageDownloadUtil.downloadImage(null, imgUrl);
		//获取姓名和职务
		String duties ;
		String name;
		if(isMSZ){
			Element nameElement = headElement.select("p").get(0);
			String str = nameElement.text();
			String[] string = str.split(" ");
			duties = string[0];
			//后面多个的话都是姓名			
			name = join(string, 1, string.length);
		}else{	
			Element nameElement = headElement.select("h1").get(0);
			String str = nameElement.text();
			String[] string = str.split("　");
			duties = string[0];
			name = join(string, 1, string.length);
		}
		//获取分工和简历
		//获取分工
		String jobDetail = jobMainElements.get(0).html();
		//获取简历
		String resume = jobMainElements.get(1).html();	
		Leader leader = new Leader(name, duties, imgUrl, jobDetail, resume,belong);
		return leader;
	}
	
	private void setLeaderUrls(Page page) throws Exception {	
		Document document = page.getHtml().getDocument();
		Elements govLeadersElement = document.select(".gov_l");
		if(govLeadersElement != null && govLeadersElement.size() > 0){
			Elements leaderDivElements = govLeadersElement.get(0).select(".leader");
			for(Element e :leaderDivElements){
				//节点下面只有一个ul
				Element ulElement = e.select("ul").get(0);
				Elements liElements = ulElement.select("li");
				for(Element e1 : liElements){
					Elements aElements = e1.select("a");
					if(aElements != null && aElements.size() > 0){
						Element aElement = aElements.get(0);
						String href = aElement.attr("href");
						href = URIUtil.getAbsoluteUrl(href, page.getRequest().getUrl());
						//webmagic会自动加上前缀
						page.addTargetRequest(href);
						//获取带蓝边的领导头像节点
						Element imgElement = aElement.select("img").get(0);
						String imgSrc = URIUtil.getAbsoluteUrl(imgElement.attr("src"), page.getRequest().getUrl());
						leaderHeadImgMap.put(href, imgSrc);
					}
				}
			}
		}
	}
	
	public String join(String[] strs , int start , int end ){
		if(strs != null ){
			StringBuilder sb = new StringBuilder();
			for( ; start < strs.length && start < end ; start++){
				sb.append(strs[start]);
			}
			return sb.toString();
		}
		return null;
	}
	
}
