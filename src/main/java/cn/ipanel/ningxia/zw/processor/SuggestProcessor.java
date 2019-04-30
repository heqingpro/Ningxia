package cn.ipanel.ningxia.zw.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.entity.Suggest;

public class SuggestProcessor implements PageProcessor{

	private static final Logger log = Logger.getLogger(SuggestProcessor.class);
	private String topicName;
	private String listRegex;
	private boolean isFirst = true;
	//该网页响应较慢,将超时时间设置长一些
	private Site site = Site.me().setTimeOut(SpiderConfig.ZW_TIMEOUT).setRetryTimes(3).setSleepTime(SpiderConfig.ZW_SLEEP_TIMES);

	public SuggestProcessor(String topicName,String listRegex) {
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
				Suggest suggest = getSuggest(page);
				page.putField("suggest", suggest);					
			} catch (Exception e) {
				log.error("类别为:"+topicName+"-----解析提议错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}else{
			try {
				if(isFirst){
					setListlUrls(page);
					isFirst = false;
				}
				setSuggestUrls(page);
				page.setSkip(true);
			} catch (Exception e) {
				log.error("类别为:"+topicName+"-----获取提议列表错误,错误URL为:---------"+url,e);
				e.printStackTrace();
			}
		}		
	}
	
	private Suggest getSuggest(Page page) throws Exception {
	
		Document document = page.getHtml().getDocument();
		Elements trElements = document.select(".gkcon-table> tbody> tr");
		//获取第一行的td
		Elements tdElements1 = trElements.get(0).select("td");
		//获取届次
		String period = tdElements1.get(0).text();
		//获取编号
		String num = tdElements1.get(1).text();
		//获取类别
		String askCategory = tdElements1.get(2).text();
		
		//获取第二行的td
		Elements tdElements2 = trElements.get(1).select("td");
		//获取主办方
		String organizer = tdElements2.get(0).text();
		//获取协办方
		String co_organizer = tdElements2.get(1).text();

		//获取三行行的td
		Elements tdElements3 = trElements.get(2).select("td");
		//获取案由
		String title = tdElements3.get(0).text();
			
		//获取正文
		Elements tdElements4 = trElements.get(3).select("td");
		String content = tdElements4.get(0).html();
		
		//获取处理情况
		Elements tdElements5 = trElements.get(4).select("td");
		String deal = tdElements5.get(0).html();

		Suggest suggest = new Suggest(num, period, topicName, askCategory, organizer, co_organizer, title, content, deal);
		return suggest;
	}
	
	private void setSuggestUrls(Page page) throws Exception {
		
		String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取提案链接表格
		Element tbodyElement = document.select(".mail_list> tbody").get(0);
		//获取表格的tr
		Elements trElements = tbodyElement.getElementsByTag("tr");
		for(int i = 1 ; i < trElements.size() ; i++){//第一个为表头，从第二个开始
			Element e = trElements.get(i);
			//每个li标签下都有a标签,图片网站下有两个a标签，同取第一个即可
			Elements aElements = e.select("a");
			if(aElements != null && aElements.size() > 0 ){
				Element aElement = aElements.get(0);	
				String href = aElement.attr("href");
//				//改为绝对路径加入列表
//				suggestUrls.add(currentHost+href);
				page.addTargetRequest(href);
			}
		}
	}
	
	
	private void setListlUrls(Page page) throws Exception {	
		/*String html = page.getHtml().toString();
		Document document = Jsoup.parse(html);
		//获取页码信息
		//获取有总页码数的节点
		Element totalElement = document.select(".page> span").get(2);
		String text = totalElement.text();
		//截取字符串获取总页码数
		String totalNumStr = text.substring(1, text.length()-1);
		Integer totalNum = Integer.valueOf(totalNumStr);
		String firstPageUrl = page.getRequest().getUrl();
		String basListUrl = firstPageUrl.substring(0, firstPageUrl.length()-1);
		//获取所有页码url
		for(int i = 2 ; i <= totalNum ; i++){
			page.addTargetRequest(basListUrl+i);
		}*/
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
		for(int i = 1 ; i < totalPage ; i++){
			page.addTargetRequest(basListUrl+"_"+i+".html");
		}
	}
	
}
