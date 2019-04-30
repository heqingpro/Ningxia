package cn.ipanel.ningxia.pipeline;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import cn.ipanel.ningxia.entity.Leader;
import cn.ipanel.ningxia.utils.HtmlUtils;

@Component
public class LeaderPipeline extends AbstractKeyGenerator<Leader> implements Pipeline{

	private List<Leader> leaders = new LinkedList<>();
	
	@Override
	public void process(ResultItems result, Task task) {

		Leader leader = result.get("leader"); 
		if(leader != null ){
			//leader每次都变，id就为随机
			leader.setId(createId(null));
			//清理样式
			leader.setJobDetail(HtmlUtils.cleanStyle(leader.getJobDetail()));
			leader.setDuties(HtmlUtils.cleanStyle(leader.getDuties()));
			//先存在入list之中，更新完成在一次性插入表之中,由于更新领导人信息时使用单线程，不存在并发问题，可用LinkedList
			leaders.add(leader);
			//leaderService.insert(leader);
		}
	}
	
	public List<Leader> getLeaders() {
		return leaders;
	}
	
	public void clearList() {
		leaders.clear();
	}

	@Override
	public String getKeyString(Leader entity) {
		return UUID.randomUUID().toString();
	}

}
