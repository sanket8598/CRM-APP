package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.ServiceFallsDto;

class ProposalServicesDtoTest {

	@Test
	void testGetterAndSetter() {
		ProposalServicesDto proposalServicesDto = new ProposalServicesDto();
		ServiceFallsDto serviceFallsMaster = new ServiceFallsDto();
		proposalServicesDto.setServiceFallsMaster(serviceFallsMaster);
		proposalServicesDto.setServicePrice("1000");
		assertNotNull(proposalServicesDto.getServiceFallsMaster());
		assertEquals(serviceFallsMaster, proposalServicesDto.getServiceFallsMaster());
		assertEquals("1000", proposalServicesDto.getServicePrice());
	}
}
