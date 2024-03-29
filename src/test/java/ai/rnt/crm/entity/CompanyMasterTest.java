package ai.rnt.crm.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CompanyMasterTest {

	CompanyMaster companyMaster = new CompanyMaster();

	@Test
	void getterTest() {
		companyMaster.getCompanyId();
		companyMaster.getCompanyName();
		companyMaster.getCompanyWebsite();
		companyMaster.getAddressLineOne();
		companyMaster.getCountry();
		companyMaster.getState();
		companyMaster.getCity();
		companyMaster.getZipCode();
		companyMaster.getContacts();
		assertNull(companyMaster.getCompanyId());
	}

	CountryMaster countryMaster = new CountryMaster();
	StateMaster stateMaster = new StateMaster();
	CityMaster cityMaster = new CityMaster();
	List<Contacts> contacts = new ArrayList<>();

	@Test
	void setterTest() {
		companyMaster.setCompanyId(1);
		companyMaster.setCompanyName("RNT");
		companyMaster.setCompanyWebsite("www.rnt.ai");
		companyMaster.setAddressLineOne("IT Tower Pune");
		companyMaster.setCountry(countryMaster);
		companyMaster.setState(stateMaster);
		companyMaster.setCity(cityMaster);
		companyMaster.setZipCode("Zu273");
		companyMaster.setContacts(contacts);
		assertEquals(1, companyMaster.getCompanyId());
	}
}
