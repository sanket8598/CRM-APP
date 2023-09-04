package ai.rnt.crm.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.service.LeadService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {
	@Override
	public ResponseEntity<String> saveLead() {
		return new ResponseEntity<String>("Lead created successfully", HttpStatus.CREATED);
	}
}
