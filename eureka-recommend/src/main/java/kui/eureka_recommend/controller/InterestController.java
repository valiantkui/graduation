package kui.eureka_recommend.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.SimpleDateFormat;

import kui.eureka_recommend.entity.Interest;
import kui.eureka_recommend.service.InterestService;

@Controller
@RequestMapping("/interest")
public class InterestController {

	
	 @Autowired
	 private  InterestService interestService;

	
	 

	 
	 @RequestMapping("/insertInterest")
	 @ResponseBody
	 public boolean insertInterest(String u_id,int n_no,float val,long timestamp) {
		 Interest interest = new Interest();
		 interest.setU_id(u_id);
		 interest.setN_no(n_no);
		 interest.setVal(val);
		 interest.setTimestamp(timestamp);
		 return interestService.insertInterest(interest);
	 }
	 
	 @RequestMapping("/insertMovie")
	 @ResponseBody
	  public  boolean insertMovie() {
		try {
			File file = new File("D:\\data\\ml-10M100K\\ratings.dat");
			FileReader fis = new FileReader(file);
			BufferedReader br = new BufferedReader(fis);
			String str = null;
			while((str=br.readLine()) != null) {
				String[] arr = str.split("::");
				String u_id = arr[0] ;
				int n_no = Integer.parseInt(arr[1]);
				float val = Float.parseFloat(arr[2]);
				long timeStamp = Integer.parseInt(arr[3]);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(new Date(timeStamp));
				Interest interest = new Interest();
				interest.setU_id(u_id);
				interest.setN_no(n_no);
				interest.setVal(val);
				interest.setTimestamp(timeStamp);
				interestService.insertInterest(interest);
			}
			br.close();
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			
		}
		return false;
		
    }
}
