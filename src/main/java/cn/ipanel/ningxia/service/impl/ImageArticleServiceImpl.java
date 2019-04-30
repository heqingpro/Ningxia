package cn.ipanel.ningxia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.ipanel.ningxia.dao.IImageArticleDao;
import cn.ipanel.ningxia.entity.ImageArticle;
import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.ImageItemBean;
import cn.ipanel.ningxia.response.RespListBean;
import cn.ipanel.ningxia.service.IImageArticleService;

@Service
public class ImageArticleServiceImpl extends BaseServiceImpl<ImageArticle> implements IImageArticleService{

	private IImageArticleDao<ImageArticle, Integer> imageArticleDao;
	@Autowired
	public void setArticleDao(IImageArticleDao<ImageArticle, Integer> imageArticleDao) {
		super.setBaseDao(imageArticleDao);
		this.imageArticleDao = imageArticleDao;
	}

	@Override
	public RespListBean getImageListBeanByTopicName(TopicPageBean page) {
		Pageable pageable = new PageRequest(page.getPage(), page.getRows(), Direction.DESC, "releaseTime");
		List<ImageItemBean> list = imageArticleDao.findByTopicName(page.getTopicName(), pageable);
		Integer count = imageArticleDao.countByTopicName(page.getTopicName()).intValue();
		RespListBean resp = getRespListBean(list, count);
		return resp;
	}
}
