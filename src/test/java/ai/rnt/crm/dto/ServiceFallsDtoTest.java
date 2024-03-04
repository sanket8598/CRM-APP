package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ServiceFallsDtoTest {

	ServiceFallsDto dto = new ServiceFallsDto();
	ServiceFallsDto dto1 = new ServiceFallsDto();

	@Test
	void testSetAndGetServiceFallsData() {
		ServiceFallsDto serviceFallsDto = new ServiceFallsDto();
		serviceFallsDto.setServiceFallsId(1);
		serviceFallsDto.setServiceName("Customer Support");
		Integer serviceFallsId = serviceFallsDto.getServiceFallsId();
		String serviceName = serviceFallsDto.getServiceName();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(serviceFallsId);
		assertEquals(1, serviceFallsId);
		assertEquals("Customer Support", serviceName);
	}
}
