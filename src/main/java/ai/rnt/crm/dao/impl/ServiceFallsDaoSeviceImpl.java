package ai.rnt.crm.dao.impl;

import static ai.rnt.crm.constants.CacheConstant.SERVICE_FALLS;
import static ai.rnt.crm.constants.CacheConstant.SERVICE_FALLS_ID;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTO;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.ServiceFallsDto;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.repository.ServiceFallRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceFallsDaoSeviceImpl implements ServiceFallsDaoSevice {

	private final ServiceFallRepository serviceFallRepository;

	@Override
	@Cacheable(value = SERVICE_FALLS, key = SERVICE_FALLS_ID)
	public Optional<ServiceFallsMaster> getServiceFallById(Integer serviceFallsId) {
		return serviceFallRepository.findById(serviceFallsId);
	}

	@Override
	@Cacheable(value = SERVICE_FALLS)
	public List<ServiceFallsMaster> getAllSerciveFalls() {
		return serviceFallRepository.findByDeletedDateIsNullOrderByServiceNameAsc();
	}

	@Override
	@Cacheable(value = SERVICE_FALLS)
	public Optional<ServiceFallsMaster> findByName(String serviceFalls) {
		return serviceFallRepository.findByServiceName(serviceFalls);
	}

	@Override
	@CacheEvict(value =SERVICE_FALLS,allEntries = true)
	public Optional<ServiceFallsDto> save(ServiceFallsMaster serviceFalls) throws Exception {
		return TO_SERVICE_FALL_MASTER_DTO.apply(serviceFallRepository.save(serviceFalls));
	}

}
