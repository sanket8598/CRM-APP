package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;

public class CountryDtoMapper {

	CountryDtoMapper() {
	}

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
			.map(dm -> TO_COUNTRY.apply(dm).get()).collect(Collectors.toList());

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
			.map(dm -> TO_COUNTRY_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<CountryMaster, Optional<CountryDto>> TO_COUNTRY_DTO_DATA = countryMaster -> {
		if (countryMaster == null) {
			return Optional.empty();
		}
		CountryDto countryDto = new CountryDto();
		countryDto.setCountryId(countryMaster.getCountryId());
		countryDto.setCountry(countryMaster.getCountry());
		countryDto.setStates(countryMaster.getStates().stream().map(stateMaster -> mapToStateDto(stateMaster))
				.collect(Collectors.toList()));
		return Optional.of(countryDto);
	};

	private static StateDto mapToStateDto(StateMaster stateMaster) {
		StateDto stateDto = new StateDto();
		stateDto.setStateId(stateMaster.getStateId());
		stateDto.setState(stateMaster.getState());
		stateDto.setCities(stateMaster.getCities().stream().map(cityMaster -> mapToCityDto(cityMaster))
				.collect(Collectors.toList()));
		return stateDto;
	}

	private static CityDto mapToCityDto(CityMaster cityMaster) {
		CityDto cityDto = new CityDto();
		cityDto.setCityId(cityMaster.getCityId());
		cityDto.setCity(cityMaster.getCity());
		return cityDto;
	}
}
