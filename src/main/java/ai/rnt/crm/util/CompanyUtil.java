package ai.rnt.crm.util;

import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
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
			CountryDaoService countryDaoService, CompanyMasterDaoService companyMasterDaoService, UpdateLeadDto dto,
			Contacts contact) {
		log.info("inside the addUpdateCompanyDetails method...{}");
		Optional<CityMaster> existCityByName = cityDaoService.existCityByName(dto.getCity());
		Optional<StateMaster> findBystate = stateDaoService.findBystate(dto.getState());
		Optional<CountryMaster> findByCountryName = countryDaoService.findByCountryName(dto.getCountry());
		Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(dto.getCompanyName());
		if (existCompany.isPresent()) {
			CompanyMaster companyMaster = TO_COMPANY.apply(existCompany.orElseThrow(ResourceNotFoundException::new))
					.orElseThrow(ResourceNotFoundException::new);
			companyMaster.setCompanyWebsite(dto.getCompanyWebsite());
			setCompDetails(findByCountryName, findBystate, existCityByName, dto, companyMaster,
					cityDaoService, stateDaoService, countryDaoService);
			contact.setCompanyMaster(companyMaster);
			companyMasterDaoService.save(companyMaster)
					.ifPresent(comp -> TO_COMPANY.apply(comp).ifPresent(contact::setCompanyMaster));
		} else {
			CompanyMaster companyMaster = TO_COMPANY
					.apply(CompanyDto.builder().companyName(dto.getCompanyName())
							.companyWebsite(dto.getCompanyWebsite()).build())
					.orElseThrow(ResourceNotFoundException::new);
			if (findByCountryName.isPresent())
				findByCountryName.ifPresent(companyMaster::setCountry);
			else {
				CountryMaster country = new CountryMaster();
				country.setCountry(dto.getCountry());
				Optional<CountryMaster> newFindByCountryName = ofNullable(countryDaoService.addCountry(country));
				newFindByCountryName.ifPresent(companyMaster::setCountry);
			}
			setCompDetails(findByCountryName, findBystate, existCityByName, dto, companyMaster,
					cityDaoService, stateDaoService, countryDaoService);
			companyMasterDaoService.save(companyMaster)
					.ifPresent(comp -> TO_COMPANY.apply(comp).ifPresent(contact::setCompanyMaster));
		}
	}

	private static void setCompDetails(Optional<CountryMaster> findByCountryName,
			Optional<StateMaster> findBystate, Optional<CityMaster> existCityByName, UpdateLeadDto dto,
			CompanyMaster companyMaster, CityDaoService cityDaoService, StateDaoService stateDaoService,
			CountryDaoService countryDaoService) {
		if (findByCountryName.isPresent())
			findByCountryName.ifPresent(companyMaster::setCountry);
		else {
			CountryMaster country = new CountryMaster();
			country.setCountry(dto.getCountry());
			Optional<CountryMaster> newFindByCountryName = ofNullable(countryDaoService.addCountry(country));
			newFindByCountryName.ifPresent(companyMaster::setCountry);
		}
		if (findBystate.isPresent())
			findBystate.ifPresent(companyMaster::setState);
		else {
			StateMaster state = new StateMaster();
			state.setState(dto.getState());
			Optional<StateMaster> newFindBystate = ofNullable(stateDaoService.addState(state));
			newFindBystate.ifPresent(companyMaster::setState);
		}
		if (existCityByName.isPresent())
			existCityByName.ifPresent(companyMaster::setCity);
		else {
			CityMaster city = new CityMaster();
			city.setCity(dto.getCity());
			Optional<CityMaster> newExistCityByName = ofNullable(cityDaoService.addCity(city));
			newExistCityByName.ifPresent(companyMaster::setCity);
		}
		companyMaster.setZipCode(dto.getZipCode());
		companyMaster.setAddressLineOne(dto.getAddressLineOne());
	}
}
