package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.entity.CityMaster;

class CityDtoMapperTest {

	@Test
	void testToCity() {
		CityDto cityDto = new CityDto();
		Optional<CityMaster> result = CityDtoMapper.TO_CITY.apply(cityDto);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCitys() {
		List<CityDto> cityDtoList = new ArrayList<>();
		List<CityMaster> result = CityDtoMapper.TO_CITYS.apply(cityDtoList);
		assertTrue(result.isEmpty());
	}

	@Test
	void testToCityDto() {
		CityMaster cityMaster = new CityMaster();
		Optional<CityDto> result = CityDtoMapper.TO_CITY_DTO.apply(cityMaster);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCityDtos() {
		List<CityMaster> cityMasterList = new ArrayList<>();
		List<CityDto> result = CityDtoMapper.TO_CITY_DTOS.apply(cityMasterList);
		assertTrue(result.isEmpty());
	}
}
