package com.glcnac.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("ro")//启用扫描组件
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * 配置jsp视图解析器
	 * @return 视图解析器
	 * 
	 */
	@Bean 
	public ViewResolver viewResolver(){		//配置jsp视图解析器
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setPrefix("/ro/");
		irvr.setSuffix(".jsp");
		irvr.setExposeContextBeansAsAttributes(true);//使得可以在JSP页面中通过${ }访问容器中的bean
		return irvr;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/ro").setViewName("ro");
	}

}
