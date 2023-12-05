package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTO;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.ServiceFallsDto;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.repository.ServiceFallRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceFallsDaoSeviceImpl implements ServiceFallsDaoSevice{
	
	private final ServiceFallRepository serviceFallRepository;

	@Override
	@Cacheable(value = "serviceFall",key = "#serviceFallsId")
	public Optional<ServiceFallsMaster> getServiceFallById(Integer serviceFallsId) {
		return serviceFallRepository.findById(serviceFallsId);
	}

	@Override
	@Cacheable(value="serviceFall")
	public List<ServiceFallsMaster> getAllSerciveFalls() {
		return serviceFallRepository.findByDeletedDateIsNullOrderByServiceNameAsc();
	}

	@Override
	@Cacheable(value = "serviceFall")
	public Optional<ServiceFallsMaster> findByName(String serviceFalls) {
		return serviceFallRepository.findByServiceName(serviceFalls);
	}

	@Override
	@CachePut(value="serviceFall")
	public Optional<ServiceFallsDto> save(ServiceFallsMaster serviceFalls) throws Exception {
		return TO_SERVICE_FALL_MASTER_DTO.apply(serviceFallRepository.save(serviceFalls));
	}

}
