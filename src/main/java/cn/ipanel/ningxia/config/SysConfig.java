package cn.ipanel.ningxia.config;

import java.util.HashMap;
import java.util.Map;

import cn.ipanel.ningxia.utils.PropertiesUtil;


public class SysConfig {
	//爬取内容是开启的线程数
	public static final int SPIDER_THREAD = 5;
	//是否启动就开启更新
	public static boolean UPDATE_WHEN_START = Boolean.valueOf(PropertiesUtil.getValue("config.properties", "UPDATE_WHEN_START", "false"));
	//获取项目根目录
	public static String WEB_ROOT_PATH = System.getProperty("web.root");
	//是否将图片上传到ftp
	public static boolean IS_UPLOAD_FTP = Boolean.valueOf(PropertiesUtil.getValue("config.properties", "IS_UPLOAD_FTP", "false"));
	//图片上传路径
	public static String IMAGE_UPLOAD_PATH = PropertiesUtil.getValue("config.properties", "IMAGE_UPLOAD_PATH", null);
	//图片访问路径
	public static String IMAGE_ACCESS_PATH = PropertiesUtil.getValue("config.properties", "IMAGE_ACCESS_PATH", null);
	//系统缓存文件路径
	public static String SYS_FILE_CACHE_PATH = PropertiesUtil.getValue("config.properties", "SYS_FILE_CACHE_PATH", WEB_ROOT_PATH+"/cache/");
	
	
	
	//ftp相关配置，如果配置了的话
	//ftp 主机
	public static String FTP_UPLOAD_PATH = PropertiesUtil.getValue("config.properties", "FTP_UPLOAD_PATH", null);
	//ftp 端口
	public static String FTP_HOST = PropertiesUtil.getValue("config.properties", "FTP_HOST", null);
	//ftp 用户名
	public static String FTP_PORT = PropertiesUtil.getValue("config.properties", "FTP_PORT", null);
	//ftp 密码
	public static String FTP_USER = PropertiesUtil.getValue("config.properties", "FTP_USER", null);
	//ftp上传路径
	public static String FTP_PASS = PropertiesUtil.getValue("config.properties", "FTP_PASS", null);
	//ftp上传路径
	public static String FTP_ACCESS_PATH= PropertiesUtil.getValue("config.properties", "FTP_ACCESS_PATH", null);

	
	//存储关于topic信息，来自ningxia/topic.properties
	public static final Map<String,String> TOPIC_MAP = new HashMap<String,String>();  
	
	static{
		String head = PropertiesUtil.getValue("ningxia/topic.properties", "HEAD", null);
		StringBuilder sb ;
		if(head != null ){
			//获取所有一级菜单
			String[] heads = head.split(";");
			for(String first : heads){
				//获取二级菜单字符串
				String secondeTopicStr = PropertiesUtil.getValueAfterInit(first, null);
				if(secondeTopicStr != null){
					String[] secondeTopics = secondeTopicStr.split(";");
					for(String seconde : secondeTopics){
						//获取三级菜单字符串
						String thirdTopicStr = PropertiesUtil.getValueAfterInit(seconde, null);
						
						if(thirdTopicStr != null){//含有三级菜单
							//获取三级菜单每个选项
							String[] thirdTopics = thirdTopicStr.split(";");
							
							for(String third : thirdTopics){
								sb = new StringBuilder();
								sb.append("[").append(first).append("]-[").append(seconde)
								.append("]-[").append(third).append("]");
								String topicName = sb.toString();
								TOPIC_MAP.put(third, topicName);
							}							
						}else{//两级
							sb = new StringBuilder();
							sb.append("[").append(first).append("]-[").append(seconde)
							.append("]");
							String topicName = sb.toString();
							TOPIC_MAP.put(seconde, topicName);
						}
					}
				}
			}
		}
	}
}
