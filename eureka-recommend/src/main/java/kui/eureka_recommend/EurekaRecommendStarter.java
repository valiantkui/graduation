package kui.eureka_recommend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.ibm.icu.text.SimpleDateFormat;

import kui.eureka_recommend.entity.Interest;
import kui.eureka_recommend.service.InterestService;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("kui.eureka_recommend.dao")
public class EurekaRecommendStarter 
{
    public static void main(String[] args )
    {
        SpringApplication.run(EurekaRecommendStarter.class, args);
        System.out.println("EurekaRecommendStarter");
      
    }
    
    
  
   
    
  
}
