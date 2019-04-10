package kui.eureka_center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaCenterStarter 
{
    public static void main( String[] args )
    {
       SpringApplication.run(EurekaCenterStarter.class, args);
    }
}
