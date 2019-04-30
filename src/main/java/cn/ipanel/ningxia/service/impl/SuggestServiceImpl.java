package cn.ipanel.ningxia.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.ipanel.ningxia.dao.ISuggestDao;
import cn.ipanel.ningxia.entity.Suggest;
import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.ItemBean;
import cn.ipanel.ningxia.response.RespListBean;
import cn.ipanel.ningxia.service.ISuggestService;

@Service
public class SuggestServiceImpl extends BaseServiceImpl<Suggest> implements ISuggestService {

	private ISuggestDao<Suggest, Integer> suggestDao;

	@Autowired
	public void setSuggestDao(ISuggestDao<Suggest, Integer> suggestDao) {
		super.setBaseDao(suggestDao);
		this.suggestDao = suggestDao;
	}

	@Override
	public RespListBean getListBeanByTopicOrNot(TopicPageBean page) {
		Pageable pageable = new PageRequest(page.getPage(), page.getRows(), Direction.DESC, "num");
		List<ItemBean> list;
		Integer total = null ; 
		if(StringUtils.isNotBlank(page.getTopicName())){
			list = suggestDao.findItemBeanListByTopicName(page.getTopicName(), pageable);
			total = suggestDao.countByTopicName(page.getTopicName()).intValue();
		}else{
			list = suggestDao.findItemBeanList(pageable);
			total = Long.valueOf(suggestDao.count()).intValue();
		}
		RespListBean resp = getRespListBean(list, total);
		return resp;
	}
	
}
