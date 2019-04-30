package cn.ipanel.ningxia.task;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.config.SysConfig;
import cn.ipanel.ningxia.pipeline.ArticlePipeline;
import cn.ipanel.ningxia.scheduler.SpikeFileCacheQueueScheduler;
import cn.ipanel.ningxia.utils.bean.SpiderInfo;
import cn.ipanel.ningxia.zx.constants.ZXConstants;
import cn.ipanel.ningxia.zx.processor.ZXArticleProcessor;

@Component
public class ZXSpiderTask {

	@Autowired
	private ArticlePipeline articlePipeline;
	private String cacheFilePath = SysConfig.SYS_FILE_CACHE_PATH;
	public void doUpdate(){
		startZX();
	}

	private void startZX() {
		doNormalArticleUpdate();
	}


	/**
	 *  抓取政协网下常规新闻
	 */
	private void doNormalArticleUpdate() {
		String listRegexStart = "http://www\\.nxzx\\.gov\\.cn/";
		String listRegexEnd = "/index(_\\d+)?\\.html";
		for(Map.Entry<String,SpiderInfo> entry : ZXConstants.zxArticelMap.entrySet()){
			String topicName = entry.getKey();
			topicName = SysConfig.TOPIC_MAP.get(topicName) != null ? SysConfig.TOPIC_MAP.get(topicName) : topicName ;
			SpiderInfo value = entry.getValue();
			Date deadline = value.getDeadline();
			String firstPageUrl = value.getFirstPageUrl();
			String cateId = value.getCateId();
			int maxPage = value.getMaxPage();
			String meeting = value.getMeeting();
			String listRegex = listRegexStart + cateId +listRegexEnd;
			Spider spider = Spider.create(new ZXArticleProcessor(topicName,listRegex,deadline,maxPage,meeting))
					.addUrl(firstPageUrl);
			SpikeFileCacheQueueScheduler scheduler = new SpikeFileCacheQueueScheduler(cacheFilePath+topicName);
			scheduler.setRegx(listRegex);
			spider.setScheduler(scheduler);
			spider.addPipeline(articlePipeline);
			spider.thread(SpiderConfig.ZX_RD_THREAD_COUNT).run();	
		}
		
	}
}
