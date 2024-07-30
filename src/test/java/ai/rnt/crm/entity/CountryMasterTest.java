package ai.rnt.crm.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		assertNull(countryMaster.getCountryId());
	}

	List<CompanyMaster> contacts = new ArrayList<>();
	List<StateMaster> states = new ArrayList<>();

	@Test
	void setterTest() {
		countryMaster.setCountryId(1);
		countryMaster.setCountry("India");
		countryMaster.setContacts(contacts);
		countryMaster.setStates(states);
		countryMaster.setCountryCode("+91");
		assertEquals(1, countryMaster.getCountryId());
	}
}
