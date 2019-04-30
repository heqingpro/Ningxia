package cn.ipanel.ningxia.config;


public class SpiderConfig {

	public static final int ZW_THREAD_COUNT = 6;
	
	public static final int ZW_SLEEP_TIMES = 10000;
	
	public static final int ZW_TIMEOUT = 200000;
	
	public static final int ZX_RD_THREAD_COUNT = 5 ;
	
	public static final int ZX_RD_SLEEP_TIMES = 20000;
	
	public static final int ZX_RD_TIMEOUT = 200000;
	
	//领导人更新只开启一条线程
	public static final int LEADER_THREAD_COUNT = 1 ;
	
	//是否应用第一次启动更新
	public static boolean IS_FIRST_TIME = true;
	//增量更新时获取的最大页码数
	public static final int NOT_FIRST_TIME_PAGE = 3;
	
}
