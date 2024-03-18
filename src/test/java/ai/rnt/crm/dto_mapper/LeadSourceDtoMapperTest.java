package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.LeadSourceDto;
import ai.rnt.crm.entity.LeadSourceMaster;

class LeadSourceDtoMapperTest {

	@Test
	void testToLeadSource() {
		LeadSourceDto leadSourceDto = new LeadSourceDto();
		Optional<LeadSourceMaster> leadSourceMasterOptional = LeadSourceDtoMapper.TO_LEAD_SOURCE.apply(leadSourceDto);
		assertNotNull(leadSourceMasterOptional);
	}

	@Test
	void testToLeadSources() {
		Collection<LeadSourceDto> leadSourceDtoCollection = new ArrayList<>();
		List<LeadSourceMaster> leadSourceMasterList = LeadSourceDtoMapper.TO_LEAD_SOURCES
				.apply(leadSourceDtoCollection);
		assertNotNull(leadSourceMasterList);
	}

	@Test
	void testToLeadSourceDto() {
		LeadSourceMaster leadSourceMaster = new LeadSourceMaster();
		Optional<LeadSourceDto> leadSourceDtoOptional = LeadSourceDtoMapper.TO_LEAD_SOURCE_DTO.apply(leadSourceMaster);
		assertNotNull(leadSourceDtoOptional);
	}

	@Test
	void testToLeadSourceDtos() {
		Collection<LeadSourceMaster> leadSourceMasterCollection = new ArrayList<>();
		List<LeadSourceDto> leadSourceDtoList = LeadSourceDtoMapper.TO_LEAD_SOURCE_DTOS
				.apply(leadSourceMasterCollection);
		assertNotNull(leadSourceDtoList);
	}
}
