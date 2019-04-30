package cn.ipanel.ningxia.service;

import cn.ipanel.ningxia.entity.ImageArticle;
import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.RespListBean;

public interface IImageArticleService extends IBaseService<ImageArticle>{

	RespListBean getImageListBeanByTopicName(TopicPageBean page);
	
}
