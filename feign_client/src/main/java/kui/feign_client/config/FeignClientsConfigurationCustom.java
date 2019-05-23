package kui.feign_client.config;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration  
public class FeignClientsConfigurationCustom implements RequestInterceptor {   
  @Override
  public void apply(RequestTemplate template) {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
              .getRequestAttributes();
      HttpServletRequest request = attributes.getRequest();
      String value = request.getHeader("cookie");
      System.out.println("拦截器传递的cookie:"+value);
      if(value != null) template.header("cookie",value);
  }
}  
 
