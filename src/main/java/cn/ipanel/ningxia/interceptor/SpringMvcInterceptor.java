package cn.ipanel.ningxia.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 
 *
 */
public class SpringMvcInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods","GET, POST");
		response.setHeader("Access-Control-Allow-Headers","Access-Control");
		response.setHeader("Access-Control-Allow-Credentials","true");

		return super.preHandle(request, response, handler);
	}
}
