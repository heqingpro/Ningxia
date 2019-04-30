package cn.ipanel.ningxia.utils;

import java.net.URI;

/** URI工具
 * @author qinmian
 *
 */
public class URIUtil {

	public static String getAbsoluteUrl(String href , String baseUrl) throws Exception{
		URI baseUri = new URI(baseUrl);
		return baseUri.resolve(href).toString();
	}
}
