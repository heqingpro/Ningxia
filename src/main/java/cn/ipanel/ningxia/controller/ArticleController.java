package cn.ipanel.ningxia.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.BaseRespBean;
import cn.ipanel.ningxia.response.RespConstants;
import cn.ipanel.ningxia.service.IArticleService;

@RequestMapping("/news")
@RestController
public class ArticleController {
	private static final Logger log = Logger.getLogger(ArticleController.class);
	@Autowired
	private IArticleService articleService;
	
	@RequestMapping(value="/list")
	public BaseRespBean getArticleList(TopicPageBean page){
		try {
			if(page == null || StringUtils.isBlank(page.getTopicName())){
				return new BaseRespBean(RespConstants.FAIL_CODE, "缺少查询参数"); 
			}
			return articleService.getListBeanByTopicName(page);
		} catch (Exception e) {
			log.error("查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
		
	}
/*	
	@RequestMapping(value="/list_by_belong")
	public BaseRespBean getArticleListByBelong(TopicPageBean page){
		try {
			if(page == null || StringUtils.isBlank(page.getBelong())){
				return new BaseRespBean(RespConstants.FAIL_CODE, "缺少查询参数"); 
			}
			return articleService.getListBeanByBelong(page);
		} catch (Exception e) {
			log.error("查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
		
	}*/
	 
	/**  获取栏目下的滚动图片新闻
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/image")
	public BaseRespBean getArticleImgListByBelong(TopicPageBean page){
		try {
			if(page == null || StringUtils.isBlank(page.getTopicName())){
				return new BaseRespBean(RespConstants.FAIL_CODE, "缺少查询参数"); 
			}
			return articleService.getImgListBeanByTopicName(page);
		} catch (Exception e) {
			log.error("查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
		
	}
	
	@RequestMapping(value="/get_item")
	public BaseRespBean getArticleById(@RequestParam("id") String id){
		try {
			if(StringUtils.isBlank(id)){
				return new BaseRespBean(RespConstants.FAIL_CODE, "缺少查询参数"); 
			}
			return articleService.selectOneById(id);
		} catch (Exception e) {
			log.error("新闻ID为"+id+" , 查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
	}
}
