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
      Enumeration<String> headerNames = request.getHeaderNames();
      System.out.println("headerNames:"+headerNames);
      if (headerNames != null) {
          while (headerNames.hasMoreElements()) {
              String name = headerNames.nextElement();
              String values = request.getHeader(name);
              System.out.println("正在传递：name:"+name+",,values:"+values);
              template.header(name, values);
          }
          System.out.println("feign interceptor header:"+template);
      }
  }
}  
 
