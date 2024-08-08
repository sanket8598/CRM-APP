package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ai.rnt.crm.entity.CurrencyMaster;
import ai.rnt.crm.repository.CurrencyRepository;

@ExtendWith(MockitoExtension.class)
 class CurrencyDaoServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyDaoServiceImpl currencyDaoService;
    
    @Test
    void testAllCurrencies() {
        List<CurrencyMaster> currencies = List.of(new CurrencyMaster());
        
        when(currencyRepository.findAll()).thenReturn(currencies);

        List<CurrencyMaster> result = currencyDaoService.allCurrencies();

        assertEquals(currencies, result);
        verify(currencyRepository, times(1)).findAll();
    }

    @Test
    void testAddCurrency() {
        CurrencyMaster currencyMaster = new CurrencyMaster();
        
        when(currencyRepository.save(currencyMaster)).thenReturn(currencyMaster);

        CurrencyMaster result = currencyDaoService.addCurrency(currencyMaster);

        assertEquals(currencyMaster, result);
        verify(currencyRepository, times(1)).save(currencyMaster);
    }


    @Test
    void testFindCurrency() {
        Integer currencyId = 1;
        Optional<CurrencyMaster> currency = Optional.of(new CurrencyMaster());
        
        when(currencyRepository.findById(currencyId)).thenReturn(currency);

        Optional<CurrencyMaster> result = currencyDaoService.findCurrency(currencyId);

        assertEquals(currency, result);
        verify(currencyRepository, times(1)).findById(currencyId);
    }

    @Test
    void testFindCurrencyByName() {
        String currencyName = "Dollar";
        Optional<CurrencyMaster> currency = Optional.of(new CurrencyMaster());
        
        when(currencyRepository.findTopByCurrencyName(currencyName)).thenReturn(currency);

        Optional<CurrencyMaster> result = currencyDaoService.findCurrencyByName(currencyName);

        assertEquals(currency, result);
        verify(currencyRepository, times(1)).findTopByCurrencyName(currencyName);
    }

    @Test
    void testFindCurrencyBySymbol() {
        String currencySymbol = "$";
        Optional<CurrencyMaster> currency = Optional.of(new CurrencyMaster());
        
        when(currencyRepository.findTopByCurrencySymbol(currencySymbol)).thenReturn(currency);

        Optional<CurrencyMaster> result = currencyDaoService.findCurrencyBySymbol(currencySymbol);

        assertEquals(currency, result);
        verify(currencyRepository, times(1)).findTopByCurrencySymbol(currencySymbol);
    }

}
