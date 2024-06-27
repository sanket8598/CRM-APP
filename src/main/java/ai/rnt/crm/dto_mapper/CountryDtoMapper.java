package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.dto.CountryAndStateDto;
import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.dto.StateAndCityDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CountryMaster;
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
		countryDto.setCountryId(countryMaster.getCountryId());
		countryDto.setCountry(countryMaster.getCountry());
		countryDto.setStates(countryMaster.getStates().stream().map(CountryDtoMapper::mapToStateDto).collect(toList()));
		return of(countryDto);
	};

	private static StateAndCityDto mapToStateDto(StateMaster stateMaster) {
		StateAndCityDto stateDto = new StateAndCityDto();
		stateDto.setStateId(stateMaster.getStateId());
		stateDto.setState(stateMaster.getState());
		stateDto.setCities(stateMaster.getCities().stream().map(CountryDtoMapper::mapToCityDto).collect(toList()));
		return stateDto;
	}

	private static CityDto mapToCityDto(CityMaster cityMaster) {
		CityDto cityDto = new CityDto();
		cityDto.setCityId(cityMaster.getCityId());
		cityDto.setCity(cityMaster.getCity());
		return cityDto;
	}
}
