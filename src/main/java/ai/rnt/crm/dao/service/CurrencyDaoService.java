package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.CurrencyDto;
import ai.rnt.crm.entity.CurrencyMaster;

public interface CurrencyDaoService extends CrudService<CurrencyMaster, CurrencyDto>{

	List<CurrencyMaster> allCurrencies();
	
	CurrencyMaster addCurrency(CurrencyMaster currencyMaster);
	
	Optional<CurrencyMaster> findCurrency(Integer currencyId);

	Optional<CurrencyMaster> findCurrencyByName(String currencyName);
	
	Optional<CurrencyMaster> findCurrencyBySymbol(String currencySymbol);

}
