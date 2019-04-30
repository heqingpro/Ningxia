package cn.ipanel.ningxia.zx.constants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.ipanel.ningxia.utils.PropertiesUtil;
import cn.ipanel.ningxia.utils.TimeUtil;
import cn.ipanel.ningxia.utils.bean.SpiderInfo;

/**政协网
 * @author qinmian
 *
 */
public class ZXConstants {

	public static final String BASE_URL = "http://www.nxzx.gov.cn";
	//政协概况下的内容，包括领导讲话，政协简介和党派团体
	//领导讲话
	public static final String ZXLDJH = "zxldjh";
	public static final String ZXLDJH_FIRST_PAGE = BASE_URL+"/ldjh/index.html";
	public static final String ZXLDJH_CATE_ID = "ldjh";
	//政协简介
	public static final String ZXJJ = "zxjj";
	public static final String ZXJJ_FIRST_PAGE = BASE_URL+"/zxjj/index.html";
	public static final String ZXJJ_CATE_ID = "zxjj";
	//党派团体
	public static final String DPTT = "dptt";
	public static final String DPTT_FIRST_PAGE = BASE_URL+"/dptt/index.html";
	public static final String DPTT_CATE_ID = "dptt";
	
	//政协会议，不将内容合并
	//政协全体会议
	public static final String ZXQTHY = "zxqthy";
	public static final String ZXQTHY_FIRST_PAGE = BASE_URL+"/zxhy/zxqthy/dsjwyh1/index.html";
	public static final String ZXQTHY_CATE_ID = "zxhy/zxqthy/dsjwyh1";
	//政协常委会议
	public static final String ZXCWHY = "zxcwhy";
	public static final String ZXCWHY_FIRST_PAGE = BASE_URL+"/zxhy/zxcwhy/dsjwyh2/index.html";
	public static final String ZXCWHY_CATE_ID = "zxhy/zxcwhy/dsjwyh2";
	//政协主席会议
	public static final String ZXZXHY = "zxzxhy";
	public static final String ZXZXHY_FIRST_PAGE = BASE_URL+"/zxhy/zxzxhy/dsjwyh3/index.html";
	public static final String ZXZXHY_CATE_ID = "zxhy/zxzxhy/dsjwyh3";
	
	//委员会工作，不将内容合并
	//提案委员会
	public static final String TAWYH = "tawyh";
	public static final String TAWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/tawyh/tadsj/index.html";
	public static final String TAWYH_CATE_ID = "zmwyhgz/tawyh/tadsj";
	//经济委员会
	public static final String JJWYH = "jjwyh";
	public static final String JJWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/jjwyh/jjdsj/index.html";
	public static final String JJWYH_CATE_ID = "zmwyhgz/jjwyh/jjdsj";
	//人口资源环境委员会
	public static final String RKZYHJWYH = "rkzyhjwyh";
	public static final String RKZYHJWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/rkzyhjwyh/rkdsj/index.html";
	public static final String RKZYHJWYH_CATE_ID = "zmwyhgz/rkzyhjwyh/rkdsj";
	//科教文卫体委员会
	public static final String KJWWTWYH = "kjwwtwyh";
	public static final String KJWWTWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/kjwwtwyh/kjdsj/index.html";
	public static final String KJWWTWYH_CATE_ID = "zmwyhgz/kjwwtwyh/kjdsj";
	//社会和法制委员会
	public static final String SHHFZWYH = "shhfzwyh";
	public static final String SHHFZWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/shhfzwyh/shdsj/index.html";
	public static final String SHHFZWYH_CATE_ID = "zmwyhgz/shhfzwyh/shdsj";
	//民族和宗教委员会
	public static final String MZHZJWYH = "mzhzjwyh";
	public static final String MZHZJWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/mzhzjwyh/mzdsj/index.html";
	public static final String MZHZJWYH_CATE_ID = "zmwyhgz/mzhzjwyh/mzdsj";
	//文史和学习委员会
	public static final String WSHXXWYH = "wshxxwyh";
	public static final String WSHXXWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/wshxxwyh/wsdsj/index.html";
	public static final String WSHXXWYH_CATE_ID = "zmwyhgz/shhfzwyh/shdsj";
	//港澳台侨联络委员会
	public static final String GATQLLWYH = "gatqllwyh";
	public static final String GATQLLWYH_FIRST_PAGE = BASE_URL+"/zmwyhgz/gatqllwyh/gadsj/index.html";
	public static final String GATQLLWYH_CATE_ID = "zmwyhgz/gatqllwyh/gadsj";
	
	//政协工作动态，内容合并，包括工作动态，市县政协，委员风采，社情民意
	//政协工作动态
	public static final String ZXGZDT = "gzdt";
	public static final String ZXGZDT_FIRST_PAGE = BASE_URL+"/zxgzdt/index.html";
	public static final String ZXGZDT_CATE_ID = "zxgzdt";
	public static Integer ZXGZDT_MAX_PAGE ;
	public static Date ZXGZDT_DEADLINE ;
	//市县政协
	public static final String SXZX = "sxzx";
	public static final String SXZX_FIRST_PAGE = BASE_URL+"/sxzx/index.html";
	public static final String SXZX_CATE_ID = "sxzx";
	public static Integer SXZX_MAX_PAGE ;
	public static Date SXZX_DEADLINE ;
	//委员风采
	public static final String WYFC = "wyfc";
	public static final String WYFC_FIRST_PAGE = BASE_URL+"/wyfc/index.html";
	public static final String WYFC_CATE_ID = "wyfc";
	public static Integer WYFC_MAX_PAGE ;
	public static Date WYFC_DEADLINE ;
	//社情民意
	public static final String SQMY = "sqmy";
	public static final String SQMY_FIRST_PAGE = BASE_URL+"/sqmy/sqmydsjwyh/index.html";
	public static final String SQMY_CATE_ID = "sqmy/sqmydsjwyh";
	
	//政协新闻，内容合并，包括热点专题，各地政协新闻，图片动态
	//热点专题
	public static final String RDZT = "zxrdzt";
	public static final String RDZT_FIRST_PAGE = BASE_URL+"/rdzt/index.html";
	public static final String RDZT_CATE_ID = "rdzt";
	//各地政协新闻
	public static final String GDZXXW = "gdzxxw";
	public static final String GDZXXW_FIRST_PAGE = BASE_URL+"/gdzxxw/index.html";
	public static final String GDZXXW_CATE_ID = "gdzxxw";
	//图片动态
	public static final String TPDT = "tpdt";
	public static final String TPDT_FIRST_PAGE = BASE_URL+"/tpdt/index.html";
	public static final String TPDT_CATE_ID = "tpdt";
	
	//政策法规,内容合并，包括规章制度，理论研究，文史资料
	//规章制度
	public static final String GZZD = "gzzd";
	public static final String GZZD_FIRST_PAGE = BASE_URL+"/gzzd/index.html";
	public static final String GZZD_CATE_ID = "gzzd";
	//理论研究
	public static final String LLYJ = "llyj";
	public static final String LLYJ_FIRST_PAGE = BASE_URL+"/llyj/index.html";
	public static final String LLYJ_CATE_ID = "llyj";
	//文史资料
	public static final String WSZL = "wszl";
	public static final String WSZL_FIRST_PAGE = BASE_URL+"/wszl/index.html";
	public static final String WSZL_CATE_ID = "wszl";
	
	//通用委员会
	public static final String COMMON_MEETING = "第十届委员会";
	//终端显示时所属栏目，政协只在一个栏目下
	public static final String ZZQZX_BELONG = "zzqzx";
	//通用配置
	public static Date ZX_COMMON_DEADLINE ;
	public static Integer ZX_COMMON_MAX_PAGE;
	
	//合并的topicName
	public static final String ZXGK_TOPIC_NAME = "zxgk";
	public static final String ZXGZDT_TOPIC_NAME = "zxgzdt";
	public static final String ZXXW_TOPIC_NAME = "zxxw";
	public static final String ZCFG_TOPIC_NAME = "zcfg";
	
	public static final Map<String,SpiderInfo> zxArticelMap = new HashMap<String,SpiderInfo>();	
	static{
		
		try {
			//政协工作动态配置
			ZXGZDT_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValue("ningxia/zx.properties", "ZXGZDT_MAX_PAGE", "7"));
			ZXGZDT_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("ZXGZDT_DEADLINE", null), "yyyy-MM-dd");
			//市县政协配置
			SXZX_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit("SXZX_MAX_PAGE", "7"));
			SXZX_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("SXZX_DEADLINE", null), "yyyy-MM-dd");
			//委员风采配置
			WYFC_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit("WYFC_MAX_PAGE", "7"));
			WYFC_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("WYFC_DEADLINE", null), "yyyy-MM-dd");
			//通用配置
			ZX_COMMON_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit("ZX_COMMON_MAX_PAGE", "7"));
			ZX_COMMON_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("ZX_COMMON_DEADLINE", null), "yyyy-MM-dd");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//政协简介
		zxArticelMap.put(ZXJJ, new SpiderInfo(ZXJJ_FIRST_PAGE, ZX_COMMON_DEADLINE, ZXJJ_CATE_ID,ZX_COMMON_MAX_PAGE));
		//党派团体
		zxArticelMap.put(DPTT, new SpiderInfo(DPTT_FIRST_PAGE, ZX_COMMON_DEADLINE, DPTT_CATE_ID,ZX_COMMON_MAX_PAGE));
		//政协全体会议
		zxArticelMap.put(ZXQTHY, new SpiderInfo(ZXQTHY_FIRST_PAGE, ZX_COMMON_DEADLINE, ZXQTHY_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//政协常委会议
		zxArticelMap.put(ZXCWHY, new SpiderInfo(ZXCWHY_FIRST_PAGE, ZX_COMMON_DEADLINE, ZXCWHY_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//政协主席会议
		zxArticelMap.put(ZXZXHY, new SpiderInfo(ZXZXHY_FIRST_PAGE, ZX_COMMON_DEADLINE,ZXZXHY_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));

		//各种委员会
		zxArticelMap.put(TAWYH, new SpiderInfo(TAWYH_FIRST_PAGE, ZX_COMMON_DEADLINE, TAWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//
		zxArticelMap.put(JJWYH, new SpiderInfo(JJWYH_FIRST_PAGE, ZX_COMMON_DEADLINE,  JJWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//
		zxArticelMap.put(RKZYHJWYH, new SpiderInfo(RKZYHJWYH_FIRST_PAGE,ZX_COMMON_DEADLINE,  RKZYHJWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//
		zxArticelMap.put(KJWWTWYH, new SpiderInfo(KJWWTWYH_FIRST_PAGE, ZX_COMMON_DEADLINE, KJWWTWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//
		zxArticelMap.put(SHHFZWYH, new SpiderInfo(SHHFZWYH_FIRST_PAGE, ZX_COMMON_DEADLINE, SHHFZWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//
		zxArticelMap.put(MZHZJWYH, new SpiderInfo(MZHZJWYH_FIRST_PAGE,ZX_COMMON_DEADLINE,  MZHZJWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//
		zxArticelMap.put(WSHXXWYH, new SpiderInfo(WSHXXWYH_FIRST_PAGE, ZX_COMMON_DEADLINE,  WSHXXWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));
		//
		zxArticelMap.put(GATQLLWYH, new SpiderInfo(GATQLLWYH_FIRST_PAGE, ZX_COMMON_DEADLINE,  GATQLLWYH_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));

		//工作动态
		zxArticelMap.put(ZXGZDT, new SpiderInfo(ZXGZDT_FIRST_PAGE, ZXGZDT_DEADLINE, ZXGZDT_CATE_ID,ZXGZDT_MAX_PAGE));
		//市县政协
		zxArticelMap.put(SXZX, new SpiderInfo(SXZX_FIRST_PAGE, SXZX_DEADLINE, SXZX_CATE_ID,SXZX_MAX_PAGE));
		//委员风采
		zxArticelMap.put(WYFC, new SpiderInfo(WYFC_FIRST_PAGE, WYFC_DEADLINE, WYFC_CATE_ID,WYFC_MAX_PAGE));
		//社情民意
		zxArticelMap.put(SQMY, new SpiderInfo(SQMY_FIRST_PAGE,ZX_COMMON_DEADLINE, SQMY_CATE_ID,ZX_COMMON_MAX_PAGE, COMMON_MEETING));

		//政协新闻
		zxArticelMap.put(RDZT, new SpiderInfo(RDZT_FIRST_PAGE, ZX_COMMON_DEADLINE,  RDZT_CATE_ID, ZX_COMMON_MAX_PAGE,COMMON_MEETING));
		zxArticelMap.put(GDZXXW, new SpiderInfo(GDZXXW_FIRST_PAGE,ZX_COMMON_DEADLINE,  GDZXXW_CATE_ID, ZX_COMMON_MAX_PAGE,COMMON_MEETING));
		zxArticelMap.put(TPDT, new SpiderInfo(TPDT_FIRST_PAGE, ZX_COMMON_DEADLINE,  TPDT_CATE_ID, ZX_COMMON_MAX_PAGE,COMMON_MEETING));
		//政策法规
		zxArticelMap.put(GZZD, new SpiderInfo(GZZD_FIRST_PAGE, ZX_COMMON_DEADLINE,  GZZD_CATE_ID, ZX_COMMON_MAX_PAGE,COMMON_MEETING));
		zxArticelMap.put(LLYJ, new SpiderInfo(LLYJ_FIRST_PAGE, ZX_COMMON_DEADLINE,  LLYJ_CATE_ID, ZX_COMMON_MAX_PAGE,COMMON_MEETING));
		zxArticelMap.put(WSZL, new SpiderInfo(WSZL_FIRST_PAGE, ZX_COMMON_DEADLINE, WSZL_CATE_ID, ZX_COMMON_MAX_PAGE,COMMON_MEETING));
	}
	
}
