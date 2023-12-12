package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.DOMAIN;
import static ai.rnt.crm.constants.CacheConstant.DOMAIN_ID;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.entity.DomainMaster;
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
	public Optional<DomainMaster> addDomain(DomainMaster domainMaster){
		return ofNullable(domainMasterRepository.save(domainMaster));
	}
	
	@Override
	@Cacheable(value = DOMAIN, key = DOMAIN_ID)
	public Optional<DomainMaster> findById(Integer domainId) {
		return domainMasterRepository.findById(domainId);
	}
	
	@Override
	@Cacheable(value = DOMAIN)
	public Optional<DomainMaster> findByName(String domainName) {
		return domainMasterRepository.findByDomainName(domainName);
	}
	
	

}
