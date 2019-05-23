package kui.feign_client.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CookieService {

	public void generateUUID() {
		String uuid = UUID.randomUUID().toString();
		
		
	}
	
	
}
