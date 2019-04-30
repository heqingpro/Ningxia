package cn.ipanel.ningxia.pipeline;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import cn.ipanel.ningxia.entity.Letter;
import cn.ipanel.ningxia.service.ILetterService;
import cn.ipanel.ningxia.utils.HtmlUtils;

@Component
public class LetterPipeline extends AbstractKeyGenerator<Letter> implements Pipeline{

	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private ILetterService letterService;
	
	@Override
	public void process(ResultItems result, Task task) {

		Letter letter = result.get("letter"); 
		if(letter != null ){
			log.info("添加主席信箱");
			//产生id
			letter.setId(createId(letter));
			//清除样式
			letter.setLetterContent(HtmlUtils.cleanStyle(letter.getLetterContent()));
			letter.setReplyContent(HtmlUtils.cleanStyle(letter.getReplyContent()));
			letterService.insert(letter);
		}
	}


	@Override
	public String getKeyString(Letter entity) {
		return new StringBuilder().append(entity.getSendTime())
				.append(entity.getLetterTitle())
				.append(entity.getReplyDept())
				.append(entity.getReplyTime())
				.toString();
	}
}
