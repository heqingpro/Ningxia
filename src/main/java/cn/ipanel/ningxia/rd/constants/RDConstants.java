package cn.ipanel.ningxia.rd.constants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.ipanel.ningxia.rd.utils.RDZTSpiderInfo;
import cn.ipanel.ningxia.utils.PropertiesUtil;
import cn.ipanel.ningxia.utils.TimeUtil;
import cn.ipanel.ningxia.utils.bean.SpiderInfo;

public class RDConstants {

	public static final String BASE_URL = "http://www.nxrd.gov.cn";
	
	//常委会领导
	public static final String CWHLD_FIRST_PAGE = BASE_URL+"/rdgk/ldzc/cwhld/index.html";
	//人大概况，内容合并，只有领导讲话
	//领导讲话
	public static final String RDLDJH = "rdldjh";
	public static final String RDLDJH_FIRST_PAGE = BASE_URL+"/rdgk/ldzc/ldjh/index.html";
	public static final String RDLDJH_CATE_ID = "rdgk/ldzc/ldjh";
	
	//人大新闻,内容合并，但是发现图片新闻大部分与要闻重复，先不考虑图片新闻
	//人大要闻
	public static final String RDYW = "rdyw";
	public static final String RDYW_FIRST_PAGE = BASE_URL+"/rdxw/rdyw/index.html";
	public static final String RDYW_CATE_ID = "rdxw/rdyw";
	public static Integer RDYW_MAX_PAGE ;
	public static Date RDYW_DEADLINE ;
	//图片新闻
	public static final String TPXW = "tpxw";
	public static final String TPXW_FIRST_PAGE = BASE_URL+"/rdxw/tpxw/index.html";
	public static final String TPXW_CATE_ID = "rdxw/tpxw";	
	
	
	//常委会工作,不合并
	//工作计划
	public static final String GZJH = "gzjh";
	public static final String GZJH_FIRST_PAGE = BASE_URL+"/cwhgz/gzjh/index.html";
	public static final String GZJH_CATE_ID = "cwhgz/gzjh";
	//立法动态
	public static final String LFDT = "lfdt";
	public static final String LFDT_FIRST_PAGE = BASE_URL+"/cwhgz/lfdt/index.html";
	public static final String LFDT_CATE_ID = "cwhgz/lfdt";
	//监督视窗
	public static final String JDSC = "jdsc";
	public static final String JDSC_FIRST_PAGE = BASE_URL+"/cwhgz/jdsc/index.html";
	public static final String JDSC_CATE_ID = "cwhgz/jdsc";
	//重大事项决定
	public static final String ZDSXJD = "zdsxjd";
	public static final String ZDSXJD_FIRST_PAGE = BASE_URL+"/cwhgz/zdsxjd/index.html";
	public static final String ZDSXJD_CATE_ID = "cwhgz/zdsxjd";
	//人事任免
	public static final String RDRSRM = "rdrsrm";
	public static final String RDRSRM_FIRST_PAGE = BASE_URL+"/cwhgz/rsrm/index.html";
	public static final String RDRSRM_CATE_ID = "cwhgz/rsrm";
	
	//工作动态，不合并
	//通知公告
	public static final String RDTZGG = "rdtzgg";
	public static final String RDTZGG_FIRST_PAGE = BASE_URL+"/gzdt/tzgg/index.html";
	public static final String RDTZGG_CATE_ID = "gzdt/tzgg";
	//自身建设
	public static final String ZSJS = "zsjs";
	public static final String ZSJS_FIRST_PAGE = BASE_URL+"/gzdt/zsjs/index.html";
	public static final String ZSJS_CATE_ID = "gzdt/zsjs";
	//市区县人大
	public static final String SQXRD = "sxqrd";
	public static final String SQXRD_FIRST_PAGE = BASE_URL+"/gzdt/sxqrd/index.html";
	public static final String SQXRD_CATE_ID = "gzdt/sxqrd";
	public static Integer SQXRD_MAX_PAGE ;
	public static Date SQXRD_DEADLINE ;
	//代表工作
	public static final String DBGZ = "dbgz";
	public static final String DBGZ_FIRST_PAGE = BASE_URL+"/dbgz/index.html";
	public static final String DBGZ_CATE_ID = "dbgz";
	
	//人大专题，不合并
	//自治区人大常委会主任会议 ,与通用页面相同
	public static final String ZZQRDCWHZRHY = "zzqrdcwhzrhy";
	public static final String ZZQRDCWHZRHY_FIRST_PAGE = BASE_URL+"/rdzt/zhuren/index.html";
	public static final String ZZQRDCWHZRHY_CATE_ID = "rdzt/zhuren";
	//全国人代会宁夏代表团  
	public static final String QGRDHNXDBT = "qgrdhnxdbt";
	public static final String QGRDHNXDBT_FIRST_PAGE = BASE_URL+"/rdzt/qgrdhnxdbt/index.html";
	public static final String[] QGRDHNXDBT_END_URLS = {"scyw"};
	public static final String QGRDHNXDBT_SCHEDULER_STR = "http://www\\.nxrd\\.gov\\.cn/rdzt/qgrdhnxdbt.*/index(_\\d+)?\\.html";
	public static final String QGRDHNXDBT_MEETTING_REGEX = "http://www\\.nxrd\\.gov\\.cn/rdzt/qgrdhnxdbt/index(_\\d+)?\\.html";
	public static final String QGRDHNXDBT_LIST_REGEX = "http://www\\.nxrd\\.gov\\.cn/rdzt/qgrdhnxdbt/\\w+/scyw/index(_\\d+)?\\.html";
	//自治区人大代表大会   
	public static final String ZZQRMDBDH = "zzqrmdbdh";
	public static final String ZZQRMDBDH_FIRST_PAGE = BASE_URL+"/rdzt/zzqrmdbdh/index.html";
	public static final String[] ZZQRMDBDH_END_URLS = {"rdscyw"};
	public static final String ZZQRMDBDH_SCHEDULER_STR = "http://www\\.nxrd\\.gov\\.cn/rdzt/zzqrmdbdh.*/index(_\\d+)?\\.html";
	public static final String ZZQRMDBDH_MEETTING_REGEX = "http://www\\.nxrd\\.gov\\.cn/rdzt/zzqrmdbdh/index(_\\d+)?\\.html";
	public static final String ZZQRMDBDH_LIST_REGEX= "http://www\\.nxrd\\.gov\\.cn/rdzt/zzqrmdbdh/\\w+/rdscyw/index(_\\d+)?\\.html";
	//自治区人大常委会
	public static final String ZZQRDCWH = "zzqrdcwh";
	public static final String ZZQRDCWH_FIRST_PAGE = BASE_URL+"/rdzt/zzqrdcwhhy/index.html";
	public static final String[] ZZQRDCWH_END_URLS = {"sqchygg","sqcrsrm","sqcjdjy","sqctptt"};
	public static final String ZZQRDCWH_SCHEDULER_STR = "http://www\\.nxrd\\.gov\\.cn/rdzt/zzqrdcwhhy.*/index(_\\d+)?\\.html";
	public static final String ZZQRDCWH_MEETTING_REGEX = "http://www\\.nxrd\\.gov\\.cn/rdzt/zzqrdcwhhy/index(_\\d+)?\\.html";
	public static final String ZZQRDCWH_LIST_REGEX = "http://www\\.nxrd\\.gov\\.cn/rdzt/zzqrdcwhhy/\\w+/\\w+/index(_\\d+)?\\.html";
	//机关主题教育实践活动
	public static final String JGZTJYSJHD = "jgztjysjhd";
	public static final String JGZTJYSJHD_FIRST_PAGE = BASE_URL+"/rdzt/sjhd/";
	
	//通用配置
	public static Date RD_COMMON_DEADLINE ;
	public static Integer RD_COMMON_MAX_PAGE;
	public static final Map<String,SpiderInfo> rdActicleMap = new HashMap<String,SpiderInfo>();
	public static final Map<String,RDZTSpiderInfo> rdztActicleMap = new HashMap<String,RDZTSpiderInfo>();
	static{
		//加载配置
		try {
			//人大要闻
			RDYW_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValue("ningxia/rd.properties", "RDYW_MAX_PAGE", "10"));
			RDYW_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("RDYW_DEADLINE", null), "yyyy-MM-dd");
			//市区县人大
			SQXRD_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit("SQXRD_MAX_PAGE", "10"));
			SQXRD_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("SQXRD_DEADLINE", null), "yyyy-MM-dd");
			//通用配置
			RD_COMMON_MAX_PAGE = Integer.valueOf(PropertiesUtil.getValueAfterInit("RD_COMMON_MAX_PAGE", "10"));
			RD_COMMON_DEADLINE = TimeUtil.strToDate(PropertiesUtil.getValueAfterInit("RD_COMMON_DEADLINE", null), "yyyy-MM-dd");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//人大概况内容
		//领导讲话
		rdActicleMap.put(RDLDJH, new SpiderInfo(RDLDJH_FIRST_PAGE, RD_COMMON_DEADLINE, RDLDJH_CATE_ID,RD_COMMON_MAX_PAGE));
		
		//人大新闻内容
		//人大要闻
		rdActicleMap.put(RDYW, new SpiderInfo(RDYW_FIRST_PAGE, RDYW_DEADLINE, RDYW_CATE_ID,RDYW_MAX_PAGE));
		
		//常委会工作内容
		//工作计划
		rdActicleMap.put(GZJH, new SpiderInfo(GZJH_FIRST_PAGE, RD_COMMON_DEADLINE, GZJH_CATE_ID,RD_COMMON_MAX_PAGE));
		//立法动态
		rdActicleMap.put(LFDT, new SpiderInfo(LFDT_FIRST_PAGE, RD_COMMON_DEADLINE, LFDT_CATE_ID,RD_COMMON_MAX_PAGE));
		//监督视窗
		rdActicleMap.put(JDSC, new SpiderInfo(JDSC_FIRST_PAGE, RD_COMMON_DEADLINE, JDSC_CATE_ID,RD_COMMON_MAX_PAGE));
		//重大事项决定
		rdActicleMap.put(ZDSXJD, new SpiderInfo(ZDSXJD_FIRST_PAGE, RD_COMMON_DEADLINE, ZDSXJD_CATE_ID,RD_COMMON_MAX_PAGE));
		//人事任免
		rdActicleMap.put(RDRSRM, new SpiderInfo(RDRSRM_FIRST_PAGE, RD_COMMON_DEADLINE, RDRSRM_CATE_ID,RD_COMMON_MAX_PAGE));
		
		//工作动态内容
		//通知公告
		rdActicleMap.put(RDTZGG, new SpiderInfo(RDTZGG_FIRST_PAGE, RD_COMMON_DEADLINE, RDTZGG_CATE_ID,RD_COMMON_MAX_PAGE));
		//自身建设
		rdActicleMap.put(ZSJS, new SpiderInfo(ZSJS_FIRST_PAGE, RD_COMMON_DEADLINE, ZSJS_CATE_ID,RD_COMMON_MAX_PAGE));
		//市区县人大
		rdActicleMap.put(SQXRD, new SpiderInfo(SQXRD_FIRST_PAGE, SQXRD_DEADLINE, SQXRD_CATE_ID,SQXRD_MAX_PAGE));
		//代表工作
		rdActicleMap.put(DBGZ, new SpiderInfo(DBGZ_FIRST_PAGE, RD_COMMON_DEADLINE, DBGZ_CATE_ID,RD_COMMON_MAX_PAGE));
		
		//人大专题
		//人大专题 自治区人大常委会主任会议
		rdActicleMap.put(ZZQRDCWHZRHY, new SpiderInfo(ZZQRDCWHZRHY_FIRST_PAGE, RD_COMMON_DEADLINE, ZZQRDCWHZRHY_CATE_ID,RD_COMMON_MAX_PAGE));

		//人大专题下面具有二级选项的抓取
		rdztActicleMap.put(ZZQRDCWH, new RDZTSpiderInfo(ZZQRDCWH_FIRST_PAGE, ZZQRDCWH_SCHEDULER_STR, ZZQRDCWH_MEETTING_REGEX, ZZQRDCWH_LIST_REGEX, ZZQRDCWH_END_URLS, RD_COMMON_DEADLINE));
		
		rdztActicleMap.put(QGRDHNXDBT, new RDZTSpiderInfo(QGRDHNXDBT_FIRST_PAGE, QGRDHNXDBT_SCHEDULER_STR, QGRDHNXDBT_MEETTING_REGEX, QGRDHNXDBT_LIST_REGEX, QGRDHNXDBT_END_URLS, RD_COMMON_DEADLINE));
		
		rdztActicleMap.put(ZZQRMDBDH, new RDZTSpiderInfo(ZZQRMDBDH_FIRST_PAGE, ZZQRMDBDH_SCHEDULER_STR, ZZQRMDBDH_MEETTING_REGEX, ZZQRMDBDH_LIST_REGEX, ZZQRMDBDH_END_URLS,RD_COMMON_DEADLINE));

	}
}
