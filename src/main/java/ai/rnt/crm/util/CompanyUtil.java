package ai.rnt.crm.util;

import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static ai.rnt.crm.util.CommonUtil.addCurrencyDetails;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.CurrencyDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.exception.ResourceNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class CompanyUtil {

	public static void addUpdateCompanyDetails(CityDaoService cityDaoService, StateDaoService stateDaoService,
			CountryDaoService countryDaoService, CompanyMasterDaoService companyMasterDaoService,
			CurrencyDaoService currencyDaoService, UpdateLeadDto dto, Contacts contact) {
		log.info("inside the addUpdateCompanyDetails method...{}");
		Optional<CityMaster> existCityByName = cityDaoService.existCityByName(dto.getCity());
		Optional<StateMaster> findBystate = stateDaoService.findBystate(dto.getState());
		Optional<CountryMaster> findByCountryName = countryDaoService.findByCountryName(dto.getCountry());
		Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(dto.getCompanyName());
		CompanyMaster companyMaster = null;
		if (existCompany.isPresent()) {
			companyMaster = TO_COMPANY.apply(existCompany.orElseThrow(ResourceNotFoundException::new))
					.orElseThrow(ResourceNotFoundException::new);
			companyMaster.setCompanyWebsite(dto.getCompanyWebsite());
			contact.setCompanyMaster(companyMaster);
		} else
			companyMaster = TO_COMPANY
					.apply(CompanyDto.builder().companyName(dto.getCompanyName())
							.companyWebsite(dto.getCompanyWebsite()).build())
					.orElseThrow(ResourceNotFoundException::new);
		setCompDetails(findByCountryName, findBystate, existCityByName, dto, companyMaster, cityDaoService,
				stateDaoService, countryDaoService, currencyDaoService);
		companyMasterDaoService.save(companyMaster)
				.ifPresent(comp -> TO_COMPANY.apply(comp).ifPresent(contact::setCompanyMaster));
	}

	public static void setCompDetails(Optional<CountryMaster> findByCountryName, Optional<StateMaster> findBystate,
			Optional<CityMaster> existCityByName, UpdateLeadDto dto, CompanyMaster companyMaster,
			CityDaoService cityDaoService, StateDaoService stateDaoService, CountryDaoService countryDaoService,
			CurrencyDaoService currencyDaoService) {
		if (findByCountryName.isPresent()) {
			if (isNull(findByCountryName.get().getCurrency()) && nonNull(dto.getCurrency())) {
				addCurrencyDetails(currencyDaoService, dto.getCurrency().getCurrencySymbol(),
						dto.getCurrency().getCurrencyName(), dto.getCurrency().getCurrencyId())
						.ifPresent(findByCountryName.get()::setCurrency);
				companyMaster.setCountry(countryDaoService.addCountry(findByCountryName.get()));
			} else 
			 if (nonNull(dto.getCurrency()))
				addCurrencyDetails(currencyDaoService, dto.getCurrency().getCurrencySymbol(),
						dto.getCurrency().getCurrencyName(), dto.getCurrency().getCurrencyId())
						.ifPresent(findByCountryName.get()::setCurrency);
			findByCountryName.ifPresent(companyMaster::setCountry);
		} else {
			CountryMaster country = new CountryMaster();
			country.setCountry(dto.getCountry());
			addCurrencyDetails(currencyDaoService, dto.getCurrency().getCurrencySymbol(),
					dto.getCurrency().getCurrencyName(), dto.getCurrency().getCurrencyId())
					.ifPresent(country::setCurrency);
			ofNullable(countryDaoService.addCountry(country)).ifPresent(companyMaster::setCountry);
		}
		if (findBystate.isPresent())
			findBystate.ifPresent(companyMaster::setState);
		else {
			StateMaster state = new StateMaster();
			state.setState(dto.getState());
			ofNullable(stateDaoService.addState(state)).ifPresent(companyMaster::setState);
		}
		if (existCityByName.isPresent())
			existCityByName.ifPresent(companyMaster::setCity);
		else {
			CityMaster city = new CityMaster();
			city.setCity(dto.getCity());
			ofNullable(cityDaoService.addCity(city)).ifPresent(companyMaster::setCity);
		}
		companyMaster.setZipCode(dto.getZipCode());
		companyMaster.setAddressLineOne(dto.getAddressLineOne());
	}
}
