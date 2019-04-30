package cn.ipanel.ningxia.service;

import cn.ipanel.ningxia.entity.Suggest;
import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.RespListBean;

public interface ISuggestService extends IBaseService<Suggest>{
	RespListBean getListBeanByTopicOrNot(TopicPageBean page);
}
