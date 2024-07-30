package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CurrencyDtoTest {

	@Test
    void testCurrencyIdGetterSetter() {
        CurrencyDto currencyDto = new CurrencyDto();
        Integer currencyId = 101;
        currencyDto.setCurrencyId(currencyId);
        assertEquals(currencyId, currencyDto.getCurrencyId());
    }

    @Test
    void testCurrencySymbolGetterSetter() {
        CurrencyDto currencyDto = new CurrencyDto();
        String currencySymbol = "$";
        currencyDto.setCurrencySymbol(currencySymbol);
        assertEquals(currencySymbol, currencyDto.getCurrencySymbol());
    }

    @Test
    void testCurrencyNameGetterSetter() {
        CurrencyDto currencyDto = new CurrencyDto();
        String currencyName = "US Dollar";
        currencyDto.setCurrencyName(currencyName);
        assertEquals(currencyName, currencyDto.getCurrencyName());
    }
}
