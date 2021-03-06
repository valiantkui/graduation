package kui.feign_client;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Controller
public class FeignClientStarter 
{
    public static void main(String[] args )
    {
        SpringApplication.run(FeignClientStarter.class, args);
    }
    
    @RequestMapping("/")
    public String index(HttpSession session,HttpServletRequest request) {
    	System.out.println("初次请求的sessionId:"+session.getId());
    	
    	System.out.println(request.getHeader("cookie"));
    	
    	return "redirect:main.html";
    }
}
