package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DomainMasterDtoTest {

	DomainMasterDto dto = new DomainMasterDto();
	DomainMasterDto dto1 = new DomainMasterDto();

	@Test
	void getterData() {
		dto.getDomainId();
		dto.getDomainName();
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(dto);
	}

	@Test
	void setterData() {
		dto.setDomainId(1);
		dto.setDomainName("Java");
		assertNotNull(dto);
	}
}
