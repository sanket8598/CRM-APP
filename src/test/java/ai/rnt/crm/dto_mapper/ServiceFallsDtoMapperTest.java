package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.ServiceFallsDto;
import ai.rnt.crm.entity.ServiceFallsMaster;

class ServiceFallsDtoMapperTest {

	@Test
	void testToServiceFallsMaster() {
		ServiceFallsDto serviceFallsDto = new ServiceFallsDto();
		Optional<ServiceFallsMaster> serviceFallsMasterOptional = ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER
				.apply(serviceFallsDto);
		assertNotNull(serviceFallsMasterOptional);
	}

	@Test
	void testToServiceFallsMasters() {
		Collection<ServiceFallsDto> serviceFallsDtoCollection = new ArrayList<>();
		List<ServiceFallsMaster> serviceFallsMasterList = ServiceFallsDtoMapper.TO_SERVICEFALLMASTERS
				.apply(serviceFallsDtoCollection);
		assertNotNull(serviceFallsMasterList);
	}

	@Test
	void testToServiceFallsDto() {
		ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
		Optional<ServiceFallsDto> serviceFallsDtoOptional = ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTO
				.apply(serviceFallsMaster);
		assertNotNull(serviceFallsDtoOptional);
	}

	@Test
	void testToServiceFallsDtos() {
		Collection<ServiceFallsMaster> serviceFallsMasterCollection = new ArrayList<>();
		List<ServiceFallsDto> serviceFallsDtoList = ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTOS
				.apply(serviceFallsMasterCollection);
		assertNotNull(serviceFallsDtoList);
	}
}
