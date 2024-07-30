package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyMasterTest {

	@Test
	void testGettersAndSetters() {
		CurrencyMaster currencyMaster = new CurrencyMaster();

		Integer currencyId = 1;
		String currencyCode = "USD";
		String currencyName = "US Dollar";
		String currencySymbol = "$";

		List<CountryMaster> countryMasters = new ArrayList<>();
		List<Leads> leads = new ArrayList<>();
		List<Opportunity> opportunities = new ArrayList<>();

		currencyMaster.setCurrencyId(currencyId);
		currencyMaster.setCurrencyCode(currencyCode);
		currencyMaster.setCurrencyName(currencyName);
		currencyMaster.setCurrencySymbol(currencySymbol);
		currencyMaster.setCountryMaster(countryMasters);
		currencyMaster.setLead(leads);
		currencyMaster.setOpprtunity(opportunities);

		assertEquals(currencyId, currencyMaster.getCurrencyId());
		assertEquals(currencyCode, currencyMaster.getCurrencyCode());
		assertEquals(currencyName, currencyMaster.getCurrencyName());
		assertEquals(currencySymbol, currencyMaster.getCurrencySymbol());
		assertEquals(countryMasters, currencyMaster.getCountryMaster());
		assertEquals(leads, currencyMaster.getLead());
		assertEquals(opportunities, currencyMaster.getOpprtunity());
	}
}
