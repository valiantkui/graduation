package kui.eureka_recommend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.alibaba.druid.pool.DruidDataSource;
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
        
        //insertMovie();
    }
    
    
  
    
    
    public static void insertMovie() {
    	InterestService interestService = new InterestService();
		try {
			File file = new File("D:\\data\\ml-10M100K\\ratings.dat");
			FileReader fis = new FileReader(file);
			BufferedReader br = new BufferedReader(fis);
			String str = null;
			while((str=br.readLine()) != null) {
				String[] arr = str.split("::");
				String u_id = arr[0] ;
				int n_no = Integer.parseInt(arr[1]);
				int val = Integer.parseInt(arr[2]);
				long timeStamp = Integer.parseInt(arr[3]);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(new Date(timeStamp));
				Interest interest = new Interest();
				interest.setU_id(u_id);
				interest.setN_no(n_no);
				interest.setVal(val);
				interest.setTimestamp(date);
				interestService.insertInterest(interest);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
