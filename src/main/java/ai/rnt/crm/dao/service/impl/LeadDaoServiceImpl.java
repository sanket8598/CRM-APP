package ai.rnt.crm.dao.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadDaoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadDaoServiceImpl implements LeadDaoService{@Override
	public ResponseEntity<String> addLead(String lead) {
		return null;
	}
}
