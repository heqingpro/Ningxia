package cn.ipanel.ningxia.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ipanel.ningxia.response.BaseRespBean;
import cn.ipanel.ningxia.response.RespConstants;
import cn.ipanel.ningxia.service.ILeaderService;

@Controller
@RequestMapping("/leader")
public class LeaderController {

	private static final Logger log = LoggerFactory.getLogger(LeaderController.class);
	@Autowired
	private ILeaderService leaderService;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public BaseRespBean getLetterList(@RequestParam(value="belong")String belong){
		try {
			if(belong == null || "".equals(belong)){
				return new BaseRespBean(RespConstants.FAIL_CODE, "参数错误"); 
			}
			return leaderService.getListBean(belong);
		} catch (Exception e) {
			log.error("查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
	}
	@ResponseBody
	@RequestMapping(value="/get_leader")
	public BaseRespBean getArticleById(@RequestParam("id") String id){
		try {
			if(StringUtils.isBlank(id)){
				return new BaseRespBean(RespConstants.FAIL_CODE, "缺少查询参数"); 
			}
			return leaderService.selectOneById(id);
		} catch (Exception e) {
			log.error("信件ID为"+id+" , 查询失败", e);
			return new BaseRespBean(RespConstants.FAIL_CODE, "查询失败"); 
		}
	}

}
