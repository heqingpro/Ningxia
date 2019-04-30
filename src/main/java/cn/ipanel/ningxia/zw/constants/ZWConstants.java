package cn.ipanel.ningxia.zw.constants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.ipanel.ningxia.utils.PropertiesUtil;
import cn.ipanel.ningxia.utils.TimeUtil;
import cn.ipanel.ningxia.utils.bean.SpiderInfo;

public class ZWConstants {
	
	public static final String BASE_URL = "http://www.nx.gov.cn/";
//	public static final String BASE_URL = BASE_URL+"/info/iList.jsp";
	public static final String URL_SUFFIX = "/index.html";
	
	//政务动态
	public static final String ZWDT = "zwdt";
	public static final String ZWDT_CATE = "zwxx_11337/zwdt";
	public static final String ZWDT_FIRST_PAGE = BASE_URL + ZWDT_CATE + URL_SUFFIX;
	public static Integer ZWDT_MAX_PAGE ;
	public static Date ZWDT_DEADLINE ;
	//央网动态
	public static final String YWDT = "ywdt";
	public static final String YWDT_FIRST_PAGE = "http://sousuo.gov.cn/column/19423/0.htm";
	public static Integer YWDT_MAX_PAGE ;
	public static Date YWDT_DEADLINE ;
	//市县动态
	public static final String SXDT = "sxdt";
	public static final String SXDT_CATE = "zwxx_11337/sxdt";
	public static final String SXDT_FIRST_PAGE = BASE_URL+ SXDT_CATE + URL_SUFFIX;
	public static Integer SXDT_MAX_PAGE ;
	public static Date SXDT_DEADLINE ;
	//通知公告
	public static final String ZWTZGG = "zwtzgg";
	public static final String ZWTZGG_CATE = "zwgk/tzgg";
	public static final String ZWTZGG_FIRST_PAGE = BASE_URL+ ZWTZGG_CATE +URL_SUFFIX;
	public static Integer ZWTZGG_MAX_PAGE ;
	public static Date ZWTZGG_DEADLINE ;
	//政策解读
	public static final String ZCJD = "zcjd";
	public static final String ZCJD_CATE = "zwxx_11337/zcjd";
	public static final String ZCJD_FIRST_PAGE = BASE_URL+ ZCJD_CATE +URL_SUFFIX;
	public static Integer ZCJD_MAX_PAGE ;
	public static Date ZCJD_DEADLINE ;	
	//人事任免
	public static final String ZWRSRM = "zwrsrm";
	public static final String ZWRSRM_CATE = "zwgk/rsrm";
	public static final String ZWRSRM_FIRST_PAGE = BASE_URL+ ZWRSRM_CATE +URL_SUFFIX;
	
	//执政实录
	public static final String ZZSL = "zzsl";
	public static final String ZZSL_CATE = "zzsl/zzjxs";
	public static final String ZZSL_FIRST_PAGE = BASE_URL+ ZZSL_CATE +URL_SUFFIX;
	public static Integer ZZSL_MAX_PAGE ;
	public static Date ZZSL_DEADLINE ;
	//常务会议
	public static final String CWHY = "cwhy";
	public static final String CWHY_CATE = "zzsl/zfcwhy";
	public static final String CWHY_FIRST_PAGE = BASE_URL + CWHY_CATE + URL_SUFFIX;
	
	//工作报告
	public static final String GZBG = "gzbg";
	public static final String GZBG_CATE = "zzsl/zfgzbg";
	public static final String GZBG_FIRST_PAGE = BASE_URL + GZBG_CATE + URL_SUFFIX;
	
	//政府文件
	public static final String ZFWJ = "zfwj";
	public static final String ZFWJ_CATE = "zwgk/zzqzfwj";
	public static final String ZFWJ_FIRST_PAGE = BASE_URL + ZFWJ_CATE + URL_SUFFIX;
	public static Integer ZFWJ_MAX_PAGE ;
	public static Date ZFWJ_DEADLINE ;
	//政府信息公开目录
//	public static final String ZFXXGKML = "zfxxgkml";
//	public static final String ZFXXGKML_CATE = "zwgk/zzqzfwj";
//	public static final String ZFXXGKML_FIRST_PAGE = BASE_URL+"zwgk/zzqzfwj/index.html";
//	public static Integer ZFXXGKML_MAX_PAGE ;
//	public static Date ZFXXGKML_DEADLINE ;
	//政府信息公开制度
	public static final String ZFXXGKZD = "zfxxgkzd";
	public static final String ZFXXGKZD_CATE = "zwgk/zfxxgkzd";
	public static final String ZFXXGKZD_FIRST_PAGE = BASE_URL + ZFXXGKZD_CATE + URL_SUFFIX;
	
	//信息公开指南
	public static final String XXGKZN = "xxgkzn";
	public static final String XXGKZN_FIRST_PAGE = BASE_URL+"zwgk/xxgkzn/201707/t20170720_280937.html";
		
	
	//网络征集
	public static final String WLZJ = "wlzj";
	public static final String WLZJ_CATE = "hdjl/wlzj";
	public static final String WLZJ_FIRST_PAGE = BASE_URL + WLZJ_CATE + URL_SUFFIX;
	
	//专题旅游
	public static final String ZTLY = "ztly";
	public static final String ZTLY_CATE = "ssjn/ztly";
	public static final String ZTLY_FIRST_PAGE = BASE_URL + ZTLY_CATE + URL_SUFFIX;
		
	//精彩路线
	public static final String JCLX = "jclx";
	public static final String JCLX_CATE = "ssjn/jcxl";
	public static final String JCLX_FIRST_PAGE = BASE_URL + JCLX_CATE + URL_SUFFIX;
		
	//主要城市
	public static final String ZYCS = "zycs";
	public static final String ZYCS_CATE = "ssjn/zycs";
	public static final String ZYCS_FIRST_PAGE = BASE_URL + ZYCS_CATE + URL_SUFFIX;
		
	//历年民生计划
	public static final String LNMSJH = "lnmsjh";
	public static final String LNMSJH_CATE = "ztsj/zt/msss/mslnjh";
	public static final String LNMSJH_FIRST_PAGE = BASE_URL + LNMSJH_CATE + URL_SUFFIX;
		
	//民生新闻
	public static final String MSXW = "msss";
	public static final String MSXW_CATE = "ztsj/zt/msss/msxw";
	public static final String MSXW_FIRST_PAGE = BASE_URL + MSXW_CATE + URL_SUFFIX;
	public static  Integer MSXW_MAX_PAGE ;
	public static  Date MSXW_DEADLINE ;
	//脱贫攻坚
	public static final String TPGJ = "tpgj";
	public static final String TPGJ_CATE = "ztsj/zt/tpgj_1542";
	public static final String TPGJ_FIRST_PAGE = BASE_URL + TPGJ_CATE + URL_SUFFIX;
	public static Integer TPGJ_MAX_PAGE ;
	public static Date TPGJ_DEADLINE ;
	//一带一路
	public static final String YDYL = "ydyl";
	public static final String YDYL_CATE = "ztsj/zt/ydyl";
	public static final String YDYL_FIRST_PAGE = BASE_URL + YDYL_CATE + URL_SUFFIX;
	public static  Integer YDYL_MAX_PAGE ;
	public static  Date YDYL_DEADLINE ;
	//政府领导
	public static final String ZFLD_FIRST_PAGE = BASE_URL+"zzqzf/";
	
	//主席信箱
	public static final String ZXXX = "zxxx";
	//该url为获取数据的接口地址
	public static final String ZXXX_FIRST_PAGE = BASE_URL+"trsapp/appWeb.do?method=queryAppDataByFields";
	public static  Integer ZXXX_MAX_PAGE ;
	public static  Date ZXXX_DEADLINE ;
	
	//政协提案
	public static final String ZXTA = "zxta";
	public static final String ZXTA_CATE = "hdjl/zxta";
	public static final String ZXTA_FIRST_PAGE = BASE_URL + ZXTA_CATE + URL_SUFFIX;
	
	//人大建议
	public static final String RDJY = "rdjy";
	public static final String RDJY_CATE = "hdjl/rdjy";
	public static final String RDJY_FIRST_PAGE = BASE_URL + RDJY_CATE + URL_SUFFIX;
	
	//振奋精神实干兴宁
	public static final String ZFJSSGXN = "zfjssgxn";
	public static final String ZFJSSGXN_FIRST_PAGE = "http://www.nxnews.net/zt/2017/ddh/wztt/index.html";
	public static  Integer ZFJSSGXN_MAX_PAGE ;
	public static  Date ZFJSSGXN_DEADLINE ;
	
	//通用的配置
	public static Date ZW_COMMON_DEADLINE ;
	public static Integer ZW_COMMON_MAX_PAGE;

	public static final Map<String,SpiderInfo> articleMap = new HashMap<String,SpiderInfo>();
	
	public static final Map<String,SpiderInfo> imageArticleMap = new HashMap<String,SpiderInfo>();

	public static final Map<String,SpiderInfo> suggestMap = new HashMap<String,SpiderInfo>();
	
	static{
		//从配置文件之中读取配置信息
		try {
			//央网动态配置
			YWDT_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValue("ningxia/zw.properties", "YWDT_MAX_PAGE", "10"));
			YWDT_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("YWDT_DEADLINE", null), "yyyy-MM-dd");
			//政务动态
			ZWDT_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZWDT_MAX_PAGE", "10"));
			ZWDT_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZWDT_DEADLINE", null), "yyyy-MM-dd");
			//市县动态
			SXDT_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "SXDT_MAX_PAGE", "10"));
			SXDT_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "SXDT_DEADLINE", null), "yyyy-MM-dd");
			//政策解读
			ZCJD_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZCJD_MAX_PAGE", "10"));
			ZCJD_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZCJD_DEADLINE", null), "yyyy-MM-dd");
			//通知公告
			ZWTZGG_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZWTZGG_MAX_PAGE", "10"));
			ZWTZGG_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZWTZGG_DEADLINE", null), "yyyy-MM-dd");
			//执政实录
			ZZSL_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZZSL_MAX_PAGE", "10"));
			ZZSL_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZZSL_DEADLINE", null), "yyyy-MM-dd");
			//自治区政府文件
			ZFWJ_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZFWJ_MAX_PAGE", "10"));
			ZFWJ_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZFWJ_DEADLINE", null), "yyyy-MM-dd");
			//政府信息公开目录
//			ZFXXGKML_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZFXXGKML_MAX_PAGE", "10"));
//			ZFXXGKML_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZFXXGKML_DEADLINE", null), "yyyy-MM-dd");
			//主席信箱
			ZXXX_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZXXX_MAX_PAGE", "10"));
			ZXXX_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZXXX_DEADLINE", null), "yyyy-MM-dd");
			//振奋精神，实干兴宁
			ZFJSSGXN_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZFJSSGXN_MAX_PAGE", "10"));
			ZFJSSGXN_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZFJSSGXN_DEADLINE", null), "yyyy-MM-dd");
			//民生新闻
			MSXW_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "MSXW_MAX_PAGE", "10"));
			MSXW_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "MSXW_DEADLINE", null), "yyyy-MM-dd");
			//脱贫攻坚
			TPGJ_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "TPGJ_MAX_PAGE", "10"));
			TPGJ_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "TPGJ_DEADLINE", null), "yyyy-MM-dd");
			//一带一路
			YDYL_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "YDYL_MAX_PAGE", "10"));
			YDYL_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "YDYL_DEADLINE", null), "yyyy-MM-dd");
			//一带一路
			ZW_COMMON_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit( "ZW_COMMON_MAX_PAGE", "10"));
			ZW_COMMON_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit( "ZW_COMMON_DEADLINE", null), "yyyy-MM-dd");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//人事任免
		articleMap.put(ZWRSRM, new SpiderInfo(ZWRSRM_FIRST_PAGE, ZW_COMMON_DEADLINE,ZWRSRM_CATE,ZW_COMMON_MAX_PAGE));
		//市县动态
		articleMap.put(SXDT, new SpiderInfo(SXDT_FIRST_PAGE, SXDT_DEADLINE,SXDT_CATE,SXDT_MAX_PAGE));
		//政务动态
		articleMap.put(ZWDT, new SpiderInfo(ZWDT_FIRST_PAGE, ZWDT_DEADLINE,ZWDT_CATE,ZWDT_MAX_PAGE));
		//政策解读
		articleMap.put(ZCJD, new SpiderInfo(ZCJD_FIRST_PAGE, ZCJD_DEADLINE, ZCJD_CATE,ZCJD_MAX_PAGE));
		//通知公告
		articleMap.put(ZWTZGG, new SpiderInfo(ZWTZGG_FIRST_PAGE, ZWTZGG_DEADLINE, ZWTZGG_CATE,ZWTZGG_MAX_PAGE));
		
		//政府文件
		articleMap.put(ZFWJ, new SpiderInfo(ZFWJ_FIRST_PAGE, ZFWJ_DEADLINE,ZFWJ_CATE,ZFWJ_MAX_PAGE));
		//政府信息公开目录
//		articleMap.put(ZFXXGKML, new SpiderInfo(ZFXXGKML_FIRST_PAGE, ZFXXGKML_DEADLINE,"411",ZFXXGKML_MAX_PAGE));
		
		//信息公开指南，只有一个内容页，并且没有时间，不需要传
		articleMap.put(XXGKZN, new SpiderInfo(XXGKZN_FIRST_PAGE, null, "000",1));
		//政府信息公开制度
		articleMap.put(ZFXXGKZD, new SpiderInfo(ZFXXGKZD_FIRST_PAGE, ZW_COMMON_DEADLINE,ZFXXGKZD_CATE,ZW_COMMON_MAX_PAGE));		
		//网络征集
		articleMap.put(WLZJ, new SpiderInfo(WLZJ_FIRST_PAGE, ZW_COMMON_DEADLINE,WLZJ_CATE,ZW_COMMON_MAX_PAGE));
		
		//民生实事
		articleMap.put(LNMSJH, new SpiderInfo(LNMSJH_FIRST_PAGE, ZW_COMMON_DEADLINE, LNMSJH_CATE,ZW_COMMON_MAX_PAGE));
		//民生新闻
		articleMap.put(MSXW, new SpiderInfo(MSXW_FIRST_PAGE, MSXW_DEADLINE, MSXW_CATE,MSXW_MAX_PAGE));
		//脱贫攻坚
		articleMap.put(TPGJ, new SpiderInfo(TPGJ_FIRST_PAGE, TPGJ_DEADLINE,TPGJ_CATE,TPGJ_MAX_PAGE));
		//一带一路
		articleMap.put(YDYL, new SpiderInfo(YDYL_FIRST_PAGE, YDYL_DEADLINE,YDYL_CATE,YDYL_MAX_PAGE));
		//执政实录
		articleMap.put(ZZSL, new SpiderInfo(ZZSL_FIRST_PAGE, ZZSL_DEADLINE,ZZSL_CATE,ZZSL_MAX_PAGE));
		//常务会议
		articleMap.put(CWHY, new SpiderInfo(CWHY_FIRST_PAGE, ZW_COMMON_DEADLINE,CWHY_CATE,ZW_COMMON_MAX_PAGE));
		//政府工作报告
		articleMap.put(GZBG, new SpiderInfo(GZBG_FIRST_PAGE, ZW_COMMON_DEADLINE,GZBG_CATE,ZW_COMMON_MAX_PAGE));
		
		//图片新闻
		imageArticleMap.put(JCLX, new SpiderInfo(JCLX_FIRST_PAGE, ZW_COMMON_DEADLINE,JCLX_CATE,ZW_COMMON_MAX_PAGE,null));
		imageArticleMap.put(ZYCS, new SpiderInfo(ZYCS_FIRST_PAGE, ZW_COMMON_DEADLINE,ZYCS_CATE,ZW_COMMON_MAX_PAGE,null));
		imageArticleMap.put(ZTLY, new SpiderInfo(ZTLY_FIRST_PAGE, ZW_COMMON_DEADLINE,ZTLY_CATE,ZW_COMMON_MAX_PAGE,null));
		
		//人大建议和政协提案
		suggestMap.put(RDJY, new SpiderInfo(RDJY_FIRST_PAGE, ZW_COMMON_DEADLINE, RDJY_CATE,ZW_COMMON_MAX_PAGE));
		suggestMap.put(ZXTA, new SpiderInfo(ZXTA_FIRST_PAGE, ZW_COMMON_DEADLINE, ZXTA_CATE,ZW_COMMON_MAX_PAGE));
		
	}
}
