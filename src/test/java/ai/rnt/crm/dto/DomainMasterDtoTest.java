package ai.rnt.crm.dto;

import org.junit.jupiter.api.Test;

class DomainMasterDtoTest {

	DomainMasterDto dto = new DomainMasterDto();
	DomainMasterDto dto1 = new DomainMasterDto();

	@Test
	void getterData() {
		dto.getDomainId();
		dto.getDomainName();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
	}

	@Test
	void setterData() {
		dto.setDomainId(1);
		dto.setDomainName("Java");
	}
}
