package cn.ipanel.ningxia.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.config.SysConfig;
import cn.ipanel.ningxia.pipeline.ArticlePipeline;
import cn.ipanel.ningxia.pipeline.ImageArticlePipeline;
import cn.ipanel.ningxia.pipeline.LeaderPipeline;
import cn.ipanel.ningxia.pipeline.LetterPipeline;
import cn.ipanel.ningxia.pipeline.SuggestPipeline;
import cn.ipanel.ningxia.scheduler.SpikeFileCacheQueueScheduler;
import cn.ipanel.ningxia.utils.bean.SpiderInfo;
import cn.ipanel.ningxia.zw.constants.ZWConstants;
import cn.ipanel.ningxia.zw.processor.ImageArticleProcessor;
import cn.ipanel.ningxia.zw.processor.LeaderProcessor;
import cn.ipanel.ningxia.zw.processor.LetterProcessor;
import cn.ipanel.ningxia.zw.processor.SGXNArticleProcessor;
import cn.ipanel.ningxia.zw.processor.SuggestProcessor;
import cn.ipanel.ningxia.zw.processor.YWDTArticleProcessor;
import cn.ipanel.ningxia.zw.processor.ZWArticleProcessor;

/** 爬虫任务，定时开启
 * @author qinmian
 *
 */
@Component
public class ZWSpiderTask {
	
	private Logger log = Logger.getLogger(getClass());
	
	private String fileCachePath = SysConfig.SYS_FILE_CACHE_PATH;
	
	@Autowired
	private ArticlePipeline articlePipeline;
	@Autowired
	private LeaderPipeline leaderPipeline;
	@Autowired
	private LetterPipeline letterPipeline;
	@Autowired
	private SuggestPipeline suggestPipeline;
	@Autowired
	private ImageArticlePipeline imageArticlePipeline;
	
	public void doUpdate(){
		startZW();
	}

	private void startZW() {
		doNormalArticleUpdate();
		doSGXNArticleUpdate();
		doYWDTArticleUpdate();
		doLetterUpdate();
		doSuggestUpdate();
		doImageArticleUpdate();
		doLeaderUpdate();
	}

	/**
	 * 更新人大建议和政协提案
	 */
	private void doSuggestUpdate() {
//		String listRegexStart = "http://www\\.nx\\.gov\\.cn/info/iList\\.jsp.+cat_id=";
//		String listRegexEnd = "&cur_page=\\d++";
		String listRegexStart = "http://www\\.nx\\.gov\\.cn/";
		String listRegexEnd = "/index(_\\d+)?\\.html";
		for(Map.Entry<String,SpiderInfo> entry : ZWConstants.suggestMap.entrySet()){
			String topicName = entry.getKey();
			SpiderInfo value = entry.getValue();
			String firstPageUrl = value.getFirstPageUrl();
			String cateId = value.getCateId();
			String listRegex = listRegexStart + cateId +listRegexEnd;
			Spider spider = Spider.create(new SuggestProcessor(topicName,listRegex))
					.addUrl(firstPageUrl);
			SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(fileCachePath+topicName);
			scheduler.setRegx(listRegex);
			spider.setScheduler(scheduler);
			spider.addPipeline(suggestPipeline);
			spider.thread(SpiderConfig.ZW_THREAD_COUNT).run();	
		}	
	}

	/** @Date 2017-11-07
	 *  内容更新，更新获取方式
	 *  主席信箱更新
	 */
	private void doLetterUpdate() {
		List<String> urls = getLetterUrls();
		String[] urlArray = new String[urls.size()];
		urls.toArray(urlArray);
//		String listRegx = "http://www\\.nx\\.gov\\.cn/appeal/list\\.jsp\\?model_id=30&cur_page=\\d++";				
		Spider spider = Spider.create(new LetterProcessor(ZWConstants.ZXXX_DEADLINE))
				.addUrl(urls.toArray(urlArray));
		SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(fileCachePath+ZWConstants.ZXXX);
		scheduler.setRegx("");
		spider.setScheduler(scheduler);
		spider.addPipeline(letterPipeline);
		spider.thread(5).run();
	}

	/** 
	 * @Description 2017-11-07界面更新，获取方式产生变化，需要post方式调用接口，根据返回值再得到每个url 
	 * @author qinmian
	 * @Date 2017年11月7日 下午1:54:42
	 * @return
	 */
	public List<String> getLetterUrls() {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		try {
			RequestConfig requestConfig = RequestConfig.custom()    
			        .setConnectTimeout(15000).setConnectionRequestTimeout(10000)    
			        .setSocketTimeout(15000).build();    
			client = HttpClients.createDefault();
			//计算获取的数据数量,定的时候每页显示20，总记录数就是每页数量×最大页码
			Integer total = ZWConstants.ZXXX_MAX_PAGE * 20 ;
			HttpPost post = new HttpPost(ZWConstants.ZXXX_FIRST_PAGE);
			post.setConfig(requestConfig);
			//设置请求参数
			post.addHeader("content-type", "application/x-www-form-urlencoded");
			post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
			
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("name","sy"));
			list.add(new BasicNameValuePair("appId","348"));
			list.add(new BasicNameValuePair("groupId","293"));
			//只取一页，数量为总数量
			list.add(new BasicNameValuePair("pageNum","1"));
			list.add(new BasicNameValuePair("field","TITLE,REPLYTIME"));
			list.add(new BasicNameValuePair("status","-1"));
			list.add(new BasicNameValuePair("isHome","0"));
			list.add(new BasicNameValuePair("numPerPage",total.toString()));
			list.add(new BasicNameValuePair("orderField","crtime"));
			list.add(new BasicNameValuePair("orderDirection","desc"));
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(list);
			post.setEntity(postEntity);
			response = client.execute(post);
			int code = response.getStatusLine().getStatusCode();
			if(code == 200){
				HttpEntity responseEntity = response.getEntity();
				String html = EntityUtils.toString(responseEntity, "UTF-8");
				//解析数据
				Document document = Jsoup.parse(html);
				Elements trElements = document.select(".zf-flex-table-inside> tbody> tr");
				if(trElements != null && trElements.size() > 0 ){
					String base = "http://www.nx.gov.cn/trsapp/appWeb.do?method=appDataDetail&groupId=293&appId=348&dataId=";
					List<String> urls = new ArrayList<String>();
					for(Element element :trElements){
						Elements tdElements = element.select("td");
						if(tdElements == null || tdElements.size() < 1){
							continue;
						}
						if(tdElements.size() > 1){//获取时间，对不满足时间要求的数据进行过滤
							String dateStr = tdElements.get(1).text();
							Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
							if(date.before(ZWConstants.ZXXX_DEADLINE)){
								continue;
							}
						}
						//获取对应id与base拼接成url
						String idStr = tdElements.get(0).select("span").get(0).attr("sqid");
						if(StringUtils.isNotBlank(idStr)){//判断是否为空
							urls.add(base + idStr);
						}
					}
				return urls;
				}
			}
			throw new Exception("错误的状态码:" + code);
		} catch (Exception e) {
			log.error("获取主席信息列表url出错", e);
		}finally{
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 *  更新领导人信息,为了保证领导人之间的顺序，只开启一个线程
	 */
	private void doLeaderUpdate() {
		Spider.create(new LeaderProcessor())
		.addUrl(ZWConstants.ZFLD_FIRST_PAGE)
		.addPipeline(leaderPipeline)
		.thread(SpiderConfig.LEADER_THREAD_COUNT)
		.run();	
	}
	
	/**
	 * 更新央网动态
	 */
	private void doYWDTArticleUpdate() {
		String listRegex = "http://sousuo\\.gov\\.cn/column/19423/\\d+\\.htm";
		String topicName = SysConfig.TOPIC_MAP.get(ZWConstants.YWDT) != null ? SysConfig.TOPIC_MAP.get(ZWConstants.YWDT) : ZWConstants.YWDT;
		Spider spider = Spider.create(new YWDTArticleProcessor(topicName,listRegex,ZWConstants.YWDT_DEADLINE,ZWConstants.YWDT_MAX_PAGE))
				.addUrl(ZWConstants.YWDT_FIRST_PAGE);
		SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(fileCachePath+topicName);
		scheduler.setRegx(listRegex);
		spider.setScheduler(scheduler);
		spider.addPipeline(articlePipeline);
		spider.thread(SpiderConfig.ZW_THREAD_COUNT).run();
	}

	/**
	 * 更新振奋精神，实干兴宁
	 */
	private void doSGXNArticleUpdate() {
		String listRegex = "http://www\\.nxnews\\.net/zt/2017/ddh/wztt/index.*\\.html";
		String topicName = SysConfig.TOPIC_MAP.get(ZWConstants.ZFJSSGXN) != null ? SysConfig.TOPIC_MAP.get(ZWConstants.ZFJSSGXN) : ZWConstants.ZFJSSGXN;
		Spider spider = Spider.create(new SGXNArticleProcessor(topicName,listRegex,ZWConstants.ZFJSSGXN_DEADLINE,ZWConstants.ZFJSSGXN_MAX_PAGE))
				.addUrl(ZWConstants.ZFJSSGXN_FIRST_PAGE);
		SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(fileCachePath+topicName);
		scheduler.setRegx(listRegex);
		spider.setScheduler(scheduler);
		spider.addPipeline(articlePipeline);
		spider.thread(SpiderConfig.ZW_THREAD_COUNT).run();	
	}

	/**
	 *  抓取政务网下常规新闻
	 */
	private void doNormalArticleUpdate() {
		String listRegexStart = "http://www\\.nx\\.gov\\.cn/";
		String listRegexEnd = "/index(_\\d+)?\\.html";
		for(Map.Entry<String,SpiderInfo> entry : ZWConstants.articleMap.entrySet()){
			String topicName = entry.getKey();
			topicName = SysConfig.TOPIC_MAP.get(topicName) != null ? SysConfig.TOPIC_MAP.get(topicName) : topicName ;
			SpiderInfo value = entry.getValue();
			Date deadline = value.getDeadline();
			String firstPageUrl = value.getFirstPageUrl();
			String cateId = value.getCateId();
			int maxPage = value.getMaxPage();
			String listRegex = listRegexStart + cateId +listRegexEnd;
			Spider spider = Spider.create(new ZWArticleProcessor(topicName,listRegex,deadline,maxPage))
					.addUrl(firstPageUrl);
			SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(fileCachePath+topicName);
			scheduler.setRegx(listRegex);
			spider.setScheduler(scheduler);
			spider.addPipeline(articlePipeline);
			spider.thread(SpiderConfig.ZW_THREAD_COUNT).run();
		}
		
	}
	
	/**
	 *  抓取政务网下塞上江南栏目下三个图片新闻
	 */
	private void doImageArticleUpdate() {
//		String listRegexStart = "http://www\\.nx\\.gov\\.cn/info/iList\\.jsp.*cat_id=";
//		String listRegexEnd = "&cur_page=\\d++";
		String listRegexStart = "http://www\\.nx\\.gov\\.cn/";
		String listRegexEnd = "/index(_\\d+)?\\.html";
		for(Map.Entry<String,SpiderInfo> entry : ZWConstants.imageArticleMap.entrySet()){
			String topicName = entry.getKey();
			SpiderInfo value = entry.getValue();
			String firstPageUrl = value.getFirstPageUrl();
			String cateId = value.getCateId();
			String listRegex = listRegexStart + cateId +listRegexEnd;
			Spider spider = Spider.create(new ImageArticleProcessor(topicName,listRegex))
					.addUrl(firstPageUrl);
			SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(fileCachePath+topicName);
			scheduler.setRegx(listRegex);
			spider.setScheduler(scheduler);
			spider.addPipeline(imageArticlePipeline);
			spider.thread(SpiderConfig.ZW_THREAD_COUNT).run();
		}
		
	}
	
}
