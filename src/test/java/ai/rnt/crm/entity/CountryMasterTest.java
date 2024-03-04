package ai.rnt.crm.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CountryMasterTest {

	CountryMaster countryMaster = new CountryMaster();

	@Test
	void getterTest() {
		countryMaster.getCountryId();
		countryMaster.getCountry();
		countryMaster.getContacts();
		countryMaster.getStates();
	}

	List<CompanyMaster> contacts = new ArrayList<>();
	List<StateMaster> states = new ArrayList<>();

	@Test
	void setterTest() {
		countryMaster.setCountryId(1);
		countryMaster.setCountry("India");
		countryMaster.setContacts(contacts);
		countryMaster.setStates(states);
	}

}
