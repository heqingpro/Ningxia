package cn.ipanel.ningxia.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ipanel.ningxia.page.BasePage;
import cn.ipanel.ningxia.response.BaseRespBean;
import cn.ipanel.ningxia.response.RespConstants;
import cn.ipanel.ningxia.service.ILetterService;

@Controller
@RequestMapping("/letter")
public class LetterController {

	private static final Logger log = LoggerFactory.getLogger(LetterController.class);
	@Autowired
	private ILetterService letterService;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public BaseRespBean getLetterList(BasePage page){
		try {
			if(page == null ){
				page = new BasePage();
			}
			return letterService.getListBean(page);
		} catch (Exception e) {
			log.error("查询失败", e);
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
			return letterService.selectOneById(id);
		} catch (Exception e) {
			log.error("信件ID为"+id+" , 查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
	}
}
