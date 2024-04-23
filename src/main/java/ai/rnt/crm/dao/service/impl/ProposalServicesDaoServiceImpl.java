package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.dto.opportunity.mapper.ProposalDtoMapper.TO_PROPOSAL_SERVICE_DTO;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.ProposalServicesDaoService;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.entity.ProposalServices;
import ai.rnt.crm.repository.ProposalServicesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProposalServicesDaoServiceImpl implements ProposalServicesDaoService {

	private final ProposalServicesRepository proposalServicesRepository;

	@Override
	public Optional<ProposalServicesDto> save(ProposalServices entity) throws Exception {
		return TO_PROPOSAL_SERVICE_DTO.apply(proposalServicesRepository.save(entity));
	}

	@Override
	public Optional<ProposalServices> findById(Integer propServiceId) {
		return proposalServicesRepository.findById(propServiceId);
	}

}
