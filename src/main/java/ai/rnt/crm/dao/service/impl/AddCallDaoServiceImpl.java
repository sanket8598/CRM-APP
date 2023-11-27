package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.AddCallDaoService;
import ai.rnt.crm.entity.Call;
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
	public Call call(Call call) {
		return addCallRepository.save(call);
	}

	@Override
	public List<Call> getCallsByLeadId(Integer leadId) {
		return addCallRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<Call> getCallById(Integer callId) {
		return addCallRepository.findById(callId);
	}
}
