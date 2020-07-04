//package br.com.api.configuracao;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class Configuracao
//{
//
//	@Bean
//	public WebMvcConfigurer forwardToIndex()
//	{
//		return new WebMvcConfigurer() {
//			@Override
//			public void addViewControllers(ViewControllerRegistry registry)
//			{
//				registry.addViewController("/").setViewName("forward:/index.html");
//			}
//		};
//	}
//
//}