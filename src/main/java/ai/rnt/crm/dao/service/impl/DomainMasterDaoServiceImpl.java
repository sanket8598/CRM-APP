package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.DOMAIN;
import static ai.rnt.crm.constants.CacheConstant.DOMAIN_ID;
import static ai.rnt.crm.dto_mapper.DomainMasterDtoMapper.TO_DOMAIN_DTO;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dto.DomainMasterDto;
import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.repository.DomainMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DomainMasterDaoServiceImpl implements DomainMasterDaoService{

	private final DomainMasterRepository domainMasterRepository;
	@Override
	@Cacheable(DOMAIN)
	public List<DomainMaster> getAllDomains(){
		return domainMasterRepository.findAll();
	}
	@Override
	@CacheEvict(value=DOMAIN,allEntries = true)
	public Optional<DomainMasterDto> addDomain(DomainMaster domainMaster){
		return TO_DOMAIN_DTO.apply(domainMasterRepository.save(domainMaster));
	}
	
	@Override
	@Cacheable(value = DOMAIN, key = DOMAIN_ID)
	public Optional<DomainMasterDto> getById(Integer domainId) {
		return TO_DOMAIN_DTO.apply(domainMasterRepository.findById(domainId).orElseThrow(()->new ResourceNotFoundException("Domain","domainId",domainId)));
	}
	
	@Override
	@Cacheable(value = DOMAIN)
	public Optional<DomainMaster> findByName(String domainName) {
		return domainMasterRepository.findByDomainName(domainName);
	}
	
	

}
