package ai.rnt.crm.api.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.entity.Company;
import ai.rnt.crm.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/lead/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeadsController {
	
	private final CompanyRepository repository;
	
	@GetMapping("/get")
	public ResponseEntity<String> getAllData(){
		return ResponseEntity.ok("werttr");
	}

	@PostMapping("/saveCompany")
	public ResponseEntity<Company> saveCompany(@RequestBody Company company){
		return ResponseEntity.ok(repository.save(company));
	}
}
