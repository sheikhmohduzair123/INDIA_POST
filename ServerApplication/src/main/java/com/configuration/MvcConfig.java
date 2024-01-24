package com.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
	
	/**
	 * Registering View Controllers
	 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/com").setViewName("com");
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/userInfo").setViewName("userInfoPage");
//        registry.addViewController("/logoutSuccessful").setViewName("logoutSuccessfulPage");
    }
    
//    @Bean
//	public FilterRegistrationBean<CustomSiteMeshFilter> siteMeshFilter(){
//		FilterRegistrationBean<CustomSiteMeshFilter> fitler = new FilterRegistrationBean<CustomSiteMeshFilter>();
//		CustomSiteMeshFilter siteMeshFilter = new CustomSiteMeshFilter();
//		fitler.setFilter(siteMeshFilter);
//		return fitler;
//	}

}
