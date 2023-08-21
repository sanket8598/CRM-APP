package ai.rnt.crm.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lead/")
public class LeadsController {
	
	@GetMapping("/get")
	public ResponseEntity<String> getAllData(){
		return ResponseEntity.ok("werttr");
	}

}
