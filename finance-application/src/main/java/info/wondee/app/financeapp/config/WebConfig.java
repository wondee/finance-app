package info.wondee.app.financeapp.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@EnableWebMvc
@EnableCaching
public class WebConfig implements WebMvcConfigurer {
 
  @Autowired
  private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

  @PostConstruct
  public void init() {
      requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("/webjars/");
      
      registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
      
      registry.addResourceHandler("/.well-known/acme-challenge/**").addResourceLocations("classpath:/static/");
      
  }
  
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/error").setViewName("error");
    registry.addViewController("/legal/impressum").setViewName("impressum");
    registry.addViewController("/legal/datenschutz").setViewName("datenschutz");
  }
}