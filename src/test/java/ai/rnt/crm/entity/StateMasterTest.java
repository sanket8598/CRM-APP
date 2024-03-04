package ai.rnt.crm.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StateMasterTest {

	StateMaster stateMaster = new StateMaster();

	@Test
	void getterTest() {
		stateMaster.getStateId();
		stateMaster.getCountry();
		stateMaster.getState();
		stateMaster.getContacts();
		stateMaster.getCities();
	}

	CountryMaster country = new CountryMaster();
	List<CompanyMaster> contacts = new ArrayList<>();
	List<CityMaster> cities = new ArrayList<>();

	@Test
	void setterTest() {
		stateMaster.setStateId(1);
		stateMaster.setCountry(country);
		stateMaster.setState("Maharashtra");
		stateMaster.setContacts(contacts);
		stateMaster.setCities(cities);
	}
}
