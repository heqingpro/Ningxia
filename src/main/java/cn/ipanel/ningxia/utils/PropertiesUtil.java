package cn.ipanel.ningxia.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class PropertiesUtil {
	private static final Logger log = Logger.getLogger(PropertiesUtil.class);

	private static Properties properties;
	private static List<String> readList;

	public static String getValue(String configFileName, String key) {
		if(readList == null ){
			readList = new ArrayList<String>();
			properties = new Properties();
		}
		if(readList.contains(configFileName)){
			return properties.getProperty(key);
		}
		InputStream in = null;
		try {
			Properties p = new Properties();
			in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(configFileName);
			p.load(in);
			properties.putAll(p);
			readList.add(configFileName);
		} catch (IOException e) {
			log.error("加载配置文件:"+configFileName+"---发生错误", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.error("关闭配置文件IO流出现错误----》:"+configFileName, e);
			}
		}
		return properties.getProperty(key);
	}

	public static String getValue(String configFileName, String key,
			String defaultValue) {
		String value = getValue(configFileName, key);
		if(StringUtils.isNotBlank(value)){
			return value;
		}
		return defaultValue;
	}
	
	/** 此方法必须在加载了相应的配置文件后才能取到值
	 * @param key
	 * @param defalut
	 * @return
	 */
	public static String getValueAfterInit(String key,String defalut){
		return StringUtils.isNotBlank(properties.getProperty(key)) ? properties.getProperty(key) : defalut;
	}
}
