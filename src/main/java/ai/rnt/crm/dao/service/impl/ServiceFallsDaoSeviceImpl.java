package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.repository.ServiceFallRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceFallsDaoSeviceImpl implements ServiceFallsDaoSevice{
	
	private final ServiceFallRepository serviceFallRepository;

	@Override
	public Optional<ServiceFallsMaster> getById(Integer serviceFallsId) {
		return serviceFallRepository.findById(serviceFallsId);
	}

	@Override
	public List<ServiceFallsMaster> getAllSerciveFalls() {
		return serviceFallRepository.findAll();
	}

}
