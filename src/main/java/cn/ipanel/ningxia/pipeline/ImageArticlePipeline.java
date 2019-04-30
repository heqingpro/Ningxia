package cn.ipanel.ningxia.pipeline;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import cn.ipanel.ningxia.entity.ImageArticle;
import cn.ipanel.ningxia.service.IImageArticleService;
import cn.ipanel.ningxia.utils.HtmlUtils;

@Component
public class ImageArticlePipeline extends AbstractKeyGenerator<ImageArticle> implements Pipeline{

	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private IImageArticleService iImageArticleService;

	@Override
	public void process(ResultItems result, Task task) {

		ImageArticle imageArticle = result.get("imageArticle"); 
		if(imageArticle != null ){
			log.info("添加ImageArticle");
			//设置id
			imageArticle.setId(createId(imageArticle));
			//清除样式
			imageArticle.setContent(HtmlUtils.cleanStyle(imageArticle.getContent()));
			iImageArticleService.insert(imageArticle);
		}
	}

	
	@Override
	public String getKeyString(ImageArticle entity) {
		return new StringBuilder().append(entity.getTitle())
				.append(entity.getTopicName())
				.append(entity.getReleaseTime())
				.toString();
	}
}
