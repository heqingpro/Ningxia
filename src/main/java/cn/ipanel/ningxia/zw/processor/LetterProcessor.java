package cn.ipanel.ningxia.zw.processor;

import java.util.Date;

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
import cn.ipanel.ningxia.entity.Letter;

public class LetterProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(LetterProcessor.class);
	private Date deadline;
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(3).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	public LetterProcessor(Date deadline) {
		this.deadline = deadline;
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String url = page.getRequest().getUrl();
		//不是列表页
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		try {
			Letter letter = getLetterByDocument(document);
			if(deadline == null){
				page.putField("letter", letter);					
			}else{
				if(letter.getReplyTime().getTime() > deadline.getTime()){
					//满足时间要求的才会添加到数据库
					page.putField("letter", letter);					
				}else{
					page.setSkip(true);
				}				
			}
		} catch (Exception e) {
			log.error("解析信件错误,错误URL为:---------"+url,e);
			e.printStackTrace();
		}
	}
	
	private Letter getLetterByDocument(Document document) throws Exception {
		
		/*Elements topTrElements = document.select(".mail_content_top> tbody> tr");
		
		Elements topTds1 = topTrElements.get(0).select("td");
		//获取来新人
		String sender = topTds1.get(0).text();
		//获取来信时间
		String sendTimeStr = topTds1.get(1).text();
		Date sendTime = DateUtils.parseDate(sendTimeStr, "yyyy-MM-dd HH:mm:ss");
		
		//获取诉求类型
		Elements topTds2 = topTrElements.get(1).select("td");
		String appeal = topTds2.get(0).text();
		//获取所属区域
		String area = topTds2.get(1).text();
		
		//获取信件内容及回复信息
		Elements mailElements = document.select(".mail_content1");
		//获取信件信息
		Elements mailTrElements = mailElements.get(0).select("tbody> tr");
		//获取来信标题
		String letterTitle = mailTrElements.get(0).select("td").get(0).text();
		//获取来信内容
		String letterContent = mailTrElements.get(1).select("td").get(0).html();

		//获取回复信息
		Elements replyTrElements = mailElements.get(1).select("tbody> tr");
		Elements replyTdElements = replyTrElements.get(0).select("td");		
		//获取回复部门
		String replyDept = replyTdElements.get(0).text();
		//获取回复时间
		String replyDateStr = replyTrElements.get(1).select("td").get(0).text();
		Date replyTime = DateUtils.parseDate(replyDateStr, "yyyy-MM-dd HH:mm:ss");
		//获取回复内容
		Element replyContentElement = replyTrElements.get(2).select("td").get(0);
		String replyContent = replyContentElement.html();*/
		Elements letterTables = document.select(".xj-tb-bk> .gz-xj-tb");
		if(letterTables != null && letterTables.size() > 0){
			//获取来信信息
			Elements trElements = letterTables.get(0).select("table> tbody> tr");
			//获取来信标题
			String letterTitle = trElements.get(0).select("td").get(0).text();
			//获取来信类型
			String appeal = trElements.get(1).select("td").get(0).text();
			//获取来信内容
			String letterContent = trElements.get(2).select("td").get(0).html();
			//获取来信时间
			String sendTimeStr = trElements.get(3).select("td").get(0).text();
			Date sendTime = DateUtils.parseDate(sendTimeStr, "yyyy-MM-dd");
			
			//获取回复信息
			Elements replyTrElements = letterTables.get(1).select("table> tbody> tr");
			//获取回复时间
			String replyDateStr = replyTrElements.get(0).select("td").get(0).text();
			Date replyTime = DateUtils.parseDate(replyDateStr, "yyyy-MM-dd");
			//获取回复部门
			String replyDept = replyTrElements.get(1).select("td").get(0).text();
			//获取回复内容和发送者
			//目前发送者只能在回复信息之中获取
			Element replyContentElement = replyTrElements.get(2).select("td").get(0);
			String replyContent = replyContentElement.html();
			//获取发送者
//			String sender = replyContentElement.child(0).text();
			Letter letter = new Letter(null, sendTime, appeal, null, letterTitle, letterContent, replyDept, replyContent, replyTime);
			return letter;
		}
		throw new Exception("无对应信件信息");
	}
	
	/*private void setLetterUrls(Page page) throws Exception {
		
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取信件链接
		Elements trElements = document.select(".mail_list> tbody> tr");
		for(int i = 1 ; i < trElements.size() ; i++ ){
			Element e = trElements.get(i);
			Elements aElements = e.select("a");
			//获取每个的a标签
			if(aElements != null && aElements.size() > 0 ){
				if(deadline != null){
					Element dateElement = e.select("td").get(2);
					String dateString = dateElement.text();
					Date date = DateUtils.parseDate(dateString, "yyyy-MM-dd");
					if(date.getTime() < deadline.getTime()){
						continue;
					}
				}
				Element aElement = aElements.get(0);
				String href = aElement.attr("href");
				page.addTargetRequest(href);
			}
		}
	}
	
	
	private void setListlUrls(Page page) throws Exception {	
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取有总页码数的节点
		Element totalElement = document.select(".page> span").get(0);
		String text = totalElement.text();
		//截取字符串获取总页码数
		String totalNumStr = text.substring(1, text.indexOf("页"));
		Integer totalNum = Integer.valueOf(totalNumStr);
		String firstPageUrl = page.getRequest().getUrl();
		String basListUrl = firstPageUrl.substring(0, firstPageUrl.length()-1);
		for(int i = 2 ; i <= totalNum && i <= maxPage ; i++){
			page.addTargetRequest(basListUrl+i);
		}
	}*/
	
}
