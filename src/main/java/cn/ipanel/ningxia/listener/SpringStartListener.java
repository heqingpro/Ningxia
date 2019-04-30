package cn.ipanel.ningxia.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.ipanel.ningxia.config.SysConfig;
import cn.ipanel.ningxia.task.SpiderTask;

/** spring启动监听器，当服务器首次启动时会开启抓取数据任务
 * @author qinmian
 *
 */
@Component
public class SpringStartListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = Logger.getLogger(SpringStartListener.class);
	@Autowired
	@Qualifier(value="spiderTask")
	private SpiderTask spiderTask;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//该方法会执行两次，分别是spring和springmvc加载完成的时候，我们要在spring加载完成时启动抓取任务
		//在springboot之中，只会调用一次
		//需要另外开启线程来跑，因为是耗时操作，会导致阻塞
		if(SysConfig.UPDATE_WHEN_START){
			log.info("应用启动进行更新操作------");
			new Thread(){
				@Override
				public void run() {
					spiderTask.doUpdate();
				}
			}.start();			
		}
		
	}

}
