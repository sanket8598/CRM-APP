package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.CURRENCY;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.CurrencyDaoService;
import ai.rnt.crm.entity.CurrencyMaster;
import ai.rnt.crm.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CurrencyDaoServiceImpl implements CurrencyDaoService {

	private final CurrencyRepository currencyRepository;
	
	
	@Override
	@Cacheable(value = CURRENCY)
	public List<CurrencyMaster> allCurrencies() {
		log.info("inside the allCurrencies method...");
		return currencyRepository.findAll();
	}


	@Override
	@CacheEvict(value = CURRENCY,allEntries = true)
	public CurrencyMaster addCurrency(CurrencyMaster currencyMaster) {
		log.info("inside the addCurrency method...");
		return currencyRepository.save(currencyMaster);
	}


	@Override
	@Cacheable(value = CURRENCY,key = "#currencyId", condition = "#currencyId!=null")
	public Optional<CurrencyMaster> findCurrency(Integer currencyId) {
		log.info("inside the findCurrency method...{}",currencyId);
		return currencyRepository.findById(currencyId);
	}
	
	@Override
	@Cacheable(value = CURRENCY,key = "#currencyName", condition = "#currencyName!=null")
	public Optional<CurrencyMaster> findCurrencyByName(String currencyName) {
		log.info("inside the findCurrencyByName method...{}",currencyName);
		return currencyRepository.findTopByCurrencyName(currencyName);
	}


	@Override
	@Cacheable(value = CURRENCY,key = "#currencySymbol", condition = "#currencySymbol!=null")
	public Optional<CurrencyMaster> findCurrencyBySymbol(String currencySymbol) {
		log.info("inside the findCurrencyBySymbol method...{}",currencySymbol);
		return currencyRepository.findTopByCurrencySymbol(currencySymbol);
	}
	
}
