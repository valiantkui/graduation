package kui.crawlingClient;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import kui.crawlingClient.controller.SohuController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class CrawlingClient 
{
    public static void main( String[] args )
    {
        SpringApplication.run(CrawlingClient.class, args);
       // dispatcher();
    }
    
}


