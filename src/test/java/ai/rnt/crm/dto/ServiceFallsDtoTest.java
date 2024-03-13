package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ServiceFallsDtoTest {

	@Test
	void testSetAndGetServiceFallsData() {
		ServiceFallsDto serviceFallsDto = new ServiceFallsDto();
		ServiceFallsDto dto = new ServiceFallsDto();
		ServiceFallsDto dto1 = new ServiceFallsDto();
		serviceFallsDto.setServiceFallsId(1);
		serviceFallsDto.setServiceName("Customer Support");
		dto1.setServiceFallsId(1);
		dto1.setServiceName("Customer Support");
		Integer serviceFallsId = serviceFallsDto.getServiceFallsId();
		String serviceName = serviceFallsDto.getServiceName();
		serviceFallsDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertNotNull(serviceFallsId);
		assertEquals(1, serviceFallsId);
		assertEquals("Customer Support", serviceName);
	}
}
