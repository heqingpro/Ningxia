package cn.ipanel.ningxia.utils;


public class HtmlUtils {

	private static final String COLOR_REGEX = "(\\w+)?(-)?color:\\s*#?(\\w+(\\(.*\\))?)(;)?";
	
	private static final String BACKGROUND_REGEX = "background(-)?(\\w+)*:\\s*#?(\\w+(\\(.*\\))?)(;)?";

	/**
	 * @Description  清除颜色相关
	 * @author qinmian
	 * @Date 2017年11月30日 下午5:13:04
	 * @param html
	 * @return
	 */
	public static String cleanStyle(String html){
		if(html == null ){
			return null;
		}
		html = html.replaceAll(BACKGROUND_REGEX, "");
		html = html.replaceAll(COLOR_REGEX, "");
		return html;
	}
	
}
