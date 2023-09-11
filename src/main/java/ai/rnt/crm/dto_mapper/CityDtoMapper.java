package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.entity.CityMaster;

public class CityDtoMapper {

	CityDtoMapper() {

	}

	/**
	 * This function will convert CityDto into optional CityMaster Entity. <b>This
	 * function will return null if passed CountryDto is null</b> <br>
	 * <b>Param</b> CityDto <br>
	 * <b>Return</b> CityMaster
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<CityDto, Optional<CityMaster>> TO_CITY = e -> evalMapper(e, CityMaster.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CityDto>, List<CityMaster>> TO_CITYS = e -> e.stream()
			.map(dm -> TO_CITY.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert CityMaster Entity into optional CityDto . <b>This
	 * function will return null if passed Company is null</b> <br>
	 * <b>Param</b> CityMaster <br>
	 * <b>Return</b> CityDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<CityMaster, Optional<CityDto>> TO_CITY_DTO = e -> evalMapper(e, CityDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CityMaster>, List<CityDto>> TO_CITY_DTOS = e -> e.stream()
			.map(dm -> TO_CITY_DTO.apply(dm).get()).collect(Collectors.toList());

}
