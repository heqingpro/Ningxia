package cn.ipanel.ningxia.service;

import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.BaseRespBean;
import cn.ipanel.ningxia.response.RespListBean;

public interface IArticleService extends IBaseService<Article>{
		
	RespListBean getListBeanByTopicName(TopicPageBean page);

	BaseRespBean getListBeanByBelong(TopicPageBean page);

	BaseRespBean getImgListBeanByTopicName(TopicPageBean page);
}
