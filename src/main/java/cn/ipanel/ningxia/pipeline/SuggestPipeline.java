package cn.ipanel.ningxia.pipeline;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import cn.ipanel.ningxia.entity.Suggest;
import cn.ipanel.ningxia.service.ISuggestService;
import cn.ipanel.ningxia.utils.HtmlUtils;

@Component
public class SuggestPipeline extends AbstractKeyGenerator<Suggest> implements Pipeline{

	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private ISuggestService suggestService;

	@Override
	public void process(ResultItems result, Task task) {

		Suggest suggest = result.get("suggest"); 
		if(suggest != null ){
			log.info("添加suggest");
			//设置id			
			suggest.setId(createId(suggest));
			//清除样式
			suggest.setContent(HtmlUtils.cleanStyle(suggest.getContent()));
			suggestService.insert(suggest);
		}
	}

	@Override
	public String getKeyString(Suggest entity) {
		return new StringBuilder().append(entity.getPeriod())
				.append(entity.getNum())
				.append(entity.getTitle())
				.append(entity.getTopicName())
				.toString();
	}

}
