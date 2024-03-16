package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.DomainMasterDto;
import ai.rnt.crm.entity.DomainMaster;

class DomainMasterDtoMapperTest {

	@Test
	void testToDomainDto() {
		DomainMaster domainMaster = new DomainMaster();
		Function<DomainMaster, Optional<DomainMasterDto>> mapper = DomainMasterDtoMapper.TO_DOMAIN_DTO;
		Optional<DomainMasterDto> result = mapper.apply(domainMaster);
		assertTrue(result.isPresent());
	}

	@Test
	void testToDomainDtos() {
		DomainMaster domainMaster1 = new DomainMaster();
		DomainMaster domainMaster2 = new DomainMaster();
		List<DomainMaster> domainMasters = new ArrayList<>();
		domainMasters.add(domainMaster1);
		domainMasters.add(domainMaster2);
		Function<Collection<DomainMaster>, List<DomainMasterDto>> mapper = DomainMasterDtoMapper.TO_DOMAIN_DTOS;
		List<DomainMasterDto> result = mapper.apply(domainMasters);
		 assertEquals(2, result.size());
	}
}
