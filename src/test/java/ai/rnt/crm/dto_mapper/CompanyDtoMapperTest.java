package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CompanyMaster;

class CompanyDtoMapperTest {

	@Test
	void testToCompany() {
		CompanyDto companyDto = new CompanyDto();
		Optional<CompanyMaster> result = CompanyDtoMapper.TO_COMPANY.apply(companyDto);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCompanys() {
		List<CompanyDto> companyDtoList = new ArrayList<>();
		List<CompanyMaster> result = CompanyDtoMapper.TO_COMPANYS.apply(companyDtoList);
		assertTrue(result.isEmpty());
	}

	@Test
	void testToCompanyDto() {
		CompanyMaster companyMaster = new CompanyMaster();
		Optional<CompanyDto> result = CompanyDtoMapper.TO_COMPANY_DTO.apply(companyMaster);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCompanyDtos() {
		List<CompanyMaster> companyMasterList = new ArrayList<>();
		List<CompanyDto> result = CompanyDtoMapper.TO_COMPANY_DTOS.apply(companyMasterList);
		assertTrue(result.isEmpty());
	}
}
