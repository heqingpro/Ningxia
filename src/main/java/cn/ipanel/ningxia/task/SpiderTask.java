package cn.ipanel.ningxia.task;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.ipanel.ningxia.config.SpiderConfig;
import cn.ipanel.ningxia.listener.SpiderFinishEvent;
import cn.ipanel.ningxia.utils.ImageDownloader;
@Component("spiderTask")
@PropertySource("classpath:application.properties")
public class SpiderTask {

	private static final Logger log = Logger.getLogger(SpiderTask.class);
	@Autowired
	private ApplicationContext applicationContext; 
	
	@Autowired
	private RDSpiderTask rdSpiderTask;
	@Autowired
	private ZXSpiderTask zxSpiderTask;
	@Autowired
	private ZWSpiderTask zwSpiderTask;
	
	private static AtomicBoolean isDoingUpdate = new AtomicBoolean(false);
	
	@Scheduled(cron="${update_cron}") 
	public void doUpdate(){
		log.info("进入更新方法之中---------------------》");
		//程序首次启动会执行，防止定时任务同时执行
		if(!isDoingUpdate.get()){
			log.info("开始执行更新操作-------------》");
			isDoingUpdate.set(true);
			long start = System.currentTimeMillis();		
			rdSpiderTask.doUpdate();
			zxSpiderTask.doUpdate();
			zwSpiderTask.doUpdate();
			long end = System.currentTimeMillis();
			long use = (end - start)/1000;
			log.info("完成更新，用时："+use + "秒");
			if(ImageDownloader.isInit){
				ImageDownloader.shutdown();
			}
			//发送抓取完成时间
			applicationContext.publishEvent(new SpiderFinishEvent(""));
			isDoingUpdate.set(false);
			SpiderConfig.IS_FIRST_TIME = false;
		}
	}
}
