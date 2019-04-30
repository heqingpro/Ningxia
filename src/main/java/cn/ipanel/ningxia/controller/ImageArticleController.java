package cn.ipanel.ningxia.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ipanel.ningxia.page.TopicPageBean;
import cn.ipanel.ningxia.response.BaseRespBean;
import cn.ipanel.ningxia.response.RespConstants;
import cn.ipanel.ningxia.service.IImageArticleService;

@Controller
@RequestMapping("/ssjn")
public class ImageArticleController {

	private static final Logger log = LoggerFactory.getLogger(ImageArticleController.class);
	@Autowired
	private IImageArticleService imageArticleService;
	
	@ResponseBody
	@RequestMapping(value="/list")
	public BaseRespBean getArticleList(TopicPageBean page){
		try {
			if(page == null || StringUtils.isBlank(page.getTopicName())){
				return new BaseRespBean(RespConstants.FAIL_CODE, "缺少查询参数"); 
			}
			return imageArticleService.getImageListBeanByTopicName(page);
		} catch (Exception e) {
			log.error("专题为："+page.getTopicName()+"查询查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/get_item")
	public BaseRespBean getArticleById(@RequestParam("id") String id){
		try {
			if(StringUtils.isBlank(id)){
				return new BaseRespBean(RespConstants.FAIL_CODE, "缺少查询参数"); 
			}
			return imageArticleService.selectOneById(id);
		} catch (Exception e) {
			log.error("塞上江南栏目ID为： "+id+" , 查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
	}
}
