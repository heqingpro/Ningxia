package cn.ipanel.ningxia.pipeline;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.service.IArticleService;
import cn.ipanel.ningxia.utils.HtmlUtils;

@Component
public class ArticlePipeline extends AbstractKeyGenerator<Article> implements Pipeline{

	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private IArticleService articleService;

	@Override
	public void process(ResultItems result, Task task) {

		Article article = result.get("article"); 
		if(article != null ){
			log.info("添加article");
			//设置id
			article.setId(createId(article));
			//清除内容之中的样式
			article.setContent(HtmlUtils.cleanStyle(article.getContent()));
			articleService.insert(article);
		}
	}

	@Override
	public String getKeyString(Article entity) {
		return new StringBuilder().append(entity.getTitle())
				.append(entity.getTopicName())
				.append(entity.getReleaseTime())
				.toString();
	}
	
}
