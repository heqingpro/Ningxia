package cn.ipanel.ningxia.task;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.config.SysConfig;
import cn.ipanel.ningxia.pipeline.ArticlePipeline;
import cn.ipanel.ningxia.pipeline.LeaderPipeline;
import cn.ipanel.ningxia.rd.constants.RDConstants;
import cn.ipanel.ningxia.rd.processor.RDArticleProcessor;
import cn.ipanel.ningxia.rd.processor.RDArticleProcessorForRDZT;
import cn.ipanel.ningxia.rd.processor.RDArticleProcessorForSJHD;
import cn.ipanel.ningxia.rd.processor.RDLeaderProcessor;
import cn.ipanel.ningxia.rd.utils.RDZTSpiderInfo;
import cn.ipanel.ningxia.scheduler.SpikeFileCacheQueueScheduler;
import cn.ipanel.ningxia.utils.bean.SpiderInfo;

/** 爬虫任务，定时开启，爬取人大网站的数据
 * @author qinmian
 *
 */
@Component
public class RDSpiderTask {
	
	@Autowired
	private ArticlePipeline articlePipeline;
	@Autowired
	private LeaderPipeline leaderPipeline;
	
	private String cacheFilePath = SysConfig.SYS_FILE_CACHE_PATH;
	
	public void doUpdate(){
		startRD();
	}

	private void startRD() {
		doNormalArticleUpdate();
		doRDZTUpdate();
		doRDSJHDUpdate();
		doRDLeaderUpdate();
	}

	/**
	 *  抓取人大网站下领导数据，为保证顺序，值开启一条线程
	 */
	public void doRDLeaderUpdate(){
		Spider spider = Spider.create(new RDLeaderProcessor())
				.addUrl(RDConstants.CWHLD_FIRST_PAGE);
		spider.addPipeline(leaderPipeline);
		spider.thread(SpiderConfig.LEADER_THREAD_COUNT).run();	
		
	}
	 
	/**
	 *  抓取人大网下常规新闻
	 */
	private void doNormalArticleUpdate() {
		String listRegexStart = "http://www\\.nxrd\\.gov\\.cn/";
		String listRegexEnd = "/index(_\\d+)?\\.html";
		for(Map.Entry<String,SpiderInfo> entry : RDConstants.rdActicleMap.entrySet()){
			String topicName = entry.getKey();
			topicName = SysConfig.TOPIC_MAP.get(topicName) != null ? SysConfig.TOPIC_MAP.get(topicName) : topicName ;
			SpiderInfo value = entry.getValue();
			Date deadline = value.getDeadline();
			String firstPageUrl = value.getFirstPageUrl();
			String cateId = value.getCateId();
			int maxPage = value.getMaxPage();
			String listRegex = listRegexStart + cateId +listRegexEnd;
			Spider spider = Spider.create(new RDArticleProcessor(topicName,listRegex,deadline,maxPage))
					.addUrl(firstPageUrl);
			SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(cacheFilePath+topicName);
			scheduler.setRegx(listRegex);
			spider.setScheduler(scheduler);
			spider.addPipeline(articlePipeline);
			spider.thread(SpiderConfig.ZX_RD_THREAD_COUNT).run();
		}
	}
	
	/**
	 *  人大网站下特殊专题更新，包括全国人代会宁夏代表团，自治区人民代表大会，自治区人大常委会
	 */
	public void doRDZTUpdate(){
		for(Map.Entry<String, RDZTSpiderInfo> entry : RDConstants.rdztActicleMap.entrySet()){
			String topicName = entry.getKey();
			topicName = SysConfig.TOPIC_MAP.get(topicName) != null ? SysConfig.TOPIC_MAP.get(topicName) : topicName ;
			RDZTSpiderInfo value = entry.getValue();
			Date deadline = value.getDeadline();
			String firstPage = value.getFirstPage();
			String listRegex = value.getListRegex();
			String meettingRegex = value.getMeettingRegex();
			String schedulerStr = value.getSchedulerStr();
			String[] urlEnds = value.getUrlEnds();
			Spider spider = Spider.create(new RDArticleProcessorForRDZT(topicName, listRegex, meettingRegex, urlEnds, deadline))
					.addUrl(firstPage);
			SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(cacheFilePath+topicName);
			scheduler.setRegx(schedulerStr);
			spider.setScheduler(scheduler);
			spider.addPipeline(articlePipeline);
			spider.thread(SpiderConfig.ZX_RD_THREAD_COUNT).run();	
		}
		
	}
	
	/**
	 *  人大网下人大专题栏目下机关主题教育实践活动
	 */
	public void doRDSJHDUpdate(){
		String pageRegex = "http://www\\.nxrd\\.gov\\.cn/rdzt/\\w+/?(\\w+)?/?(index(_\\d+)?\\.html)?";//页码页正则表达式
		String topicName = SysConfig.TOPIC_MAP.get(RDConstants.JGZTJYSJHD) != null ? SysConfig.TOPIC_MAP.get(RDConstants.JGZTJYSJHD) : RDConstants.JGZTJYSJHD;
		Spider spider = Spider.create(new RDArticleProcessorForSJHD(topicName, RDConstants.RD_COMMON_DEADLINE))
				.addUrl(RDConstants.JGZTJYSJHD_FIRST_PAGE);
		SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(cacheFilePath+topicName);
		scheduler.setRegx(pageRegex);
		spider.setScheduler(scheduler);
		spider.addPipeline(articlePipeline);
		spider.thread(SpiderConfig.ZX_RD_THREAD_COUNT).run();
		
	}
	
}
