package cn.ipanel.ningxia.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cn.ipanel.ningxia.entity.Leader;
import cn.ipanel.ningxia.pipeline.LeaderPipeline;
import cn.ipanel.ningxia.service.ILeaderService;

@Component
public class SpiderFinishListener implements ApplicationListener<SpiderFinishEvent>{

	@Autowired
	private LeaderPipeline leaderPipeline;
	@Autowired
	private ILeaderService leaderService;
	@Override
	public void onApplicationEvent(SpiderFinishEvent event) {
		List<Leader> leaders = leaderPipeline.getLeaders();
		leaderService.insertList(leaders);
		//清空list
		leaderPipeline.clearList();
	}

}
