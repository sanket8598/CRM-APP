package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static java.util.Objects.nonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.dto.CountryAndStateDto;
import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.dto.CurrencyDto;
import ai.rnt.crm.dto.StateAndCityDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.CurrencyMaster;
import ai.rnt.crm.entity.StateMaster;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class CountryDtoMapper {

	/**
	 * This function will convert CompanyDto into optional Country Entity. <b>This
	 * function will return null if passed CountryDto is null</b> <br>
	 * <b>Param</b> CountryDto <br>
	 * <b>Return</b> Country
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<CountryDto, Optional<CountryMaster>> TO_COUNTRY = e -> evalMapper(e,
			CountryMaster.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CountryDto>, List<CountryMaster>> TO_COUNTRYS = e -> e.stream()
			.map(dm -> TO_COUNTRY.apply(dm).get()).collect(toList());

	/**
	 * This function will convert Country Entity into optional CountryDto . <b>This
	 * function will return null if passed Company is null</b> <br>
	 * <b>Param</b> Country <br>
	 * <b>Return</b> CountryDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<CountryMaster, Optional<CountryDto>> TO_COUNTRY_DTO = e -> evalMapper(e,
			CountryDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CountryMaster>, List<CountryDto>> TO_COUNTRY_DTOS = e -> e.stream()
			.map(dm -> TO_COUNTRY_DTO.apply(dm).get()).collect(toList());

	public static final Function<CountryMaster, Optional<CountryAndStateDto>> TO_COUNTRY_DTO_DATA = countryMaster -> {
		if (countryMaster == null) {
			return empty();
		}
		CountryAndStateDto countryDto = new CountryAndStateDto();
		CurrencyDto currencyDto = new CurrencyDto();
		countryDto.setCountryId(countryMaster.getCountryId());
		if (nonNull(countryMaster.getCountryCode()))
			countryDto.setCountryCode(countryMaster.getCountryCode());

		countryDto.setCountry(countryMaster.getCountry());
		if (nonNull(countryMaster.getCountryFlag()))
			countryDto.setCountryFlag(countryMaster.getCountryFlag());
		if (nonNull(countryMaster.getCurrency())) {
			currencyDto.setCurrencyId(countryMaster.getCurrency().getCurrencyId());
			currencyDto.setCurrencySymbol(countryMaster.getCurrency().getCurrencySymbol());
			currencyDto.setCurrencyName(countryMaster.getCurrency().getCurrencyName());
			countryDto.setCurrency(currencyDto);
		}
		Map<Integer, StateAndCityDto> stateMap = new HashMap<>();
		for (StateMaster stateMaster : countryMaster.getStates()) {
			StateAndCityDto stateDto = stateMap.computeIfAbsent(stateMaster.getStateId(), id -> {
				StateAndCityDto newStateDto = new StateAndCityDto();
				newStateDto.setStateId(stateMaster.getStateId());
				newStateDto.setState(stateMaster.getState());
				newStateDto.setCities(new ArrayList<>());
				return newStateDto;
			});
			Map<Integer, CityDto> cityMap = stateDto.getCities().stream()
					.collect(toMap(CityDto::getCityId, city -> city));
			for (CityMaster cityMaster : stateMaster.getCities()) {
				cityMap.putIfAbsent(cityMaster.getCityId(), mapToCityDto(cityMaster));
			}
			stateDto.setCities(new ArrayList<>(cityMap.values()));
		}
		countryDto.setStates(new ArrayList<>(stateMap.values()));
		return of(countryDto);
	};

	private static CityDto mapToCityDto(CityMaster cityMaster) {
		CityDto cityDto = new CityDto();
		cityDto.setCityId(cityMaster.getCityId());
		cityDto.setCity(cityMaster.getCity());
		return cityDto;
	}

	public static final Function<CurrencyDto, Optional<CurrencyMaster>> TO_CURRENCY = e -> evalMapper(e,
			CurrencyMaster.class);
}
