package cn.ipanel.ningxia.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.WebAppRootListener;

import cn.ipanel.ningxia.interceptor.SpringMvcInterceptor;

@Configuration
public class SpringmvcConfig extends WebMvcConfigurerAdapter implements ServletContextInitializer{

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SpringMvcInterceptor()).addPathPatterns("/*/*");
	}

	/** 用于上下文监听配置
	 * @param servletContext
	 * @throws ServletException
	 */
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		servletContext.addListener(WebAppRootListener.class);
        servletContext.setInitParameter("webAppRootKey","web.root");
		
	}
}
