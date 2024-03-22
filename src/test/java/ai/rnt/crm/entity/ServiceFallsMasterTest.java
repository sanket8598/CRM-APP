package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ServiceFallsMasterTest {

	@Test
	void testServiceFallsMaster() {
		ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
		serviceFallsMaster.setServiceFallsId(1);
		serviceFallsMaster.setLeads(new ArrayList<>());
		serviceFallsMaster.getLeads();
		serviceFallsMaster.setServiceName("IT Service");
		int serviceFallsId = serviceFallsMaster.getServiceFallsId();
		String serviceName = serviceFallsMaster.getServiceName();
		assertEquals(1, serviceFallsId);
		assertEquals("IT Service", serviceName);
	}

}
