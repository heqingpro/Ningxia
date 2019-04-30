package cn.ipanel.ningxia.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.ipanel.ningxia.dao.IArticleDao;
import cn.ipanel.ningxia.entity.Article;
import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.BaseRespBean;
import cn.ipanel.ningxia.response.ImageItemBean;
import cn.ipanel.ningxia.response.ItemBean;
import cn.ipanel.ningxia.response.RespListBean;
import cn.ipanel.ningxia.service.IArticleService;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements IArticleService{

	
	private IArticleDao<Article, Integer>  articleDao;
	@Autowired
	public void setArticleDao(IArticleDao<Article, Integer> articleDao) {
		super.setBaseDao(articleDao);
		this.articleDao = articleDao;
	}

	@Override
	public RespListBean getListBeanByTopicName(TopicPageBean page) {
		Pageable pageable = new PageRequest(page.getPage(), page.getRows(), Direction.DESC, "releaseTime");
		List<ItemBean> newsList = articleDao.findByTopicNameLike("%[" + page.getTopicName() + "]%", pageable);
		Integer total = articleDao.countByTopicNameLike("%[" + page.getTopicName() + "]%").intValue();
		RespListBean resp = getRespListBean(newsList, total);
		return resp;
	}

	@Override
	public BaseRespBean getListBeanByBelong(TopicPageBean page) {
		/*Pageable pageable = new PageRequest(page.getPage(), page.getRows(), Direction.DESC, "releaseTime");
		List<ItemBean> list = articleDao.findByBelong(page.getBelong(), pageable);
		Integer total = articleDao.countByBelong(page.getBelong()).intValue();
		RespListBean resp = getRespListBean(list, total);
		return resp;*/
		return null;
	}

	@Override
	public BaseRespBean getImgListBeanByTopicName(TopicPageBean page) {
		Pageable pageable = new PageRequest(page.getPage(), page.getRows(), Direction.DESC, "releaseTime");
		List<Article> list = articleDao.getArticlesImgListByTopicName("%[" + page.getTopicName() + "]%", pageable);
		List<ImageItemBean> itemList = new ArrayList<ImageItemBean>(list.size());
		for(Article article : list){
			Document document = Jsoup.parse(article.getContent());
			String imageSrc = document.select("img").get(0).attr("src");
			itemList.add(new ImageItemBean(article.getId(), article.getTitle(), imageSrc));
		}
		Integer total = articleDao.getCountImgByTopicName("%[" + page.getTopicName() + "]%").intValue();
		RespListBean resp = getRespListBean(itemList, total);
		return resp;
	}
	
}
