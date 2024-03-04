package ai.rnt.crm.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CityMasterTest {

	CityMaster cityMaster = new CityMaster();

	@Test
	void getterTest() {
		cityMaster.getCityId();
		cityMaster.getState();
		cityMaster.getCity();
		cityMaster.getContacts();
	}

	StateMaster stateMaster = new StateMaster();
	List<CompanyMaster> contacts = new ArrayList<>();

	@Test
	void setterTest() {
		cityMaster.setCityId(1);
		cityMaster.setState(stateMaster);
		cityMaster.setCity("Pune");
		cityMaster.setContacts(contacts);
	}
}
