package ai.rnt.crm.dao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.AddCallDaoService;
import ai.rnt.crm.entity.AddCall;
import ai.rnt.crm.repository.AddCallRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AddCallDaoServiceImpl implements AddCallDaoService {

	private final AddCallRepository addCallRepository;

	@Override
	public AddCall addCall(AddCall addCall) {
		return addCallRepository.save(addCall);
	}

	@Override
	public List<AddCall> getCallsByLeadId(Integer leadId) {
		return addCallRepository.findByLeadLeadId(leadId);
	}
}