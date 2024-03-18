package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.GetVisitDto;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.entity.Visit;

class VisitDtoMapperTest {

	@Test
	void testToVisit() {
		VisitDto visitDto = new VisitDto();
		Optional<Visit> visitOptional = VisitDtoMapper.TO_VISIT.apply(visitDto);
		assertNotNull(visitOptional);
	}

	@Test
	void testToVisits() {
		Collection<VisitDto> visitDtoCollection = new ArrayList<>();
		List<Visit> visitList = VisitDtoMapper.TO_VISITS.apply(visitDtoCollection);
		assertNotNull(visitList);
	}

	@Test
	void testToVisitDto() {
		Visit visit = new Visit();
		Optional<VisitDto> visitDtoOptional = VisitDtoMapper.TO_VISIT_DTO.apply(visit);
		assertNotNull(visitDtoOptional);
	}

	@Test
	void testToVisitDtos() {
		Collection<Visit> visitCollection = new ArrayList<>();
		List<VisitDto> visitDtoList = VisitDtoMapper.TO_VISIT_DTOS.apply(visitCollection);
		assertNotNull(visitDtoList);
	}

	@Test
	void testToEditVisitDto() {
		Visit visit = new Visit();
		Optional<EditVisitDto> editVisitDtoOptional = VisitDtoMapper.TO_EDIT_VISIT_DTO.apply(visit);
		assertNotNull(editVisitDtoOptional);
	}

	@Test
	void testToEditVisitDtos() {
		Collection<Visit> visitCollection = new ArrayList<>();
		List<EditVisitDto> editVisitDtoList = VisitDtoMapper.TO_EDIT_VISIT_DTOS.apply(visitCollection);
		assertNotNull(editVisitDtoList);
	}

	@Test
	void testToGetVisitDto() {
		Visit visit = new Visit();
		Optional<GetVisitDto> getVisitDtoOptional = VisitDtoMapper.TO_GET_VISIT_DTO.apply(visit);
		assertNotNull(getVisitDtoOptional);
	}
}
