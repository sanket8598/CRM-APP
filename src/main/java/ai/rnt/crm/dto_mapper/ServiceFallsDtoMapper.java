package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.ServiceFallsDto;
import ai.rnt.crm.entity.ServiceFallsMaster;

public class ServiceFallsDtoMapper {
	
	ServiceFallsDtoMapper(){}
	
	/**
	 * This function will convert ServiceFallsDto into optional ServiceFallsMaster Entity. <b>This
	 * function will return null if passed LeadDto is null</b> <br>
	 * <b>Param</b> ServiceFallsDto <br>
	 * <b>Return</b> ServiceFallsMaster
	 * 
	 * @since 04-09-2023
	 * @version 1.0
	 */
	public static final Function<ServiceFallsDto, Optional<ServiceFallsMaster>> TO_SERVICEFALLMASTER = e -> evalMapper(e,
			ServiceFallsMaster.class);
	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<ServiceFallsDto>, List<ServiceFallsMaster>> TO_SERVICEFALLMASTERS = e -> e.stream()
			.map(dm -> TO_SERVICEFALLMASTER.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert ServiceFallsMaster Entity into optional ServiceFallsDto . <b>This
	 * function will return null if passed Company is null</b> <br>
	 * <b>Param</b> ServiceFallsMaster <br>
	 * <b>Return</b> ServiceFallsDto
	 * 
	 * @since 04-09-2023
	 * @Version 1.0
	 */
	public static final Function<ServiceFallsMaster, Optional<ServiceFallsDto>> TO_SERVICEFALLMASTER_DTO = e -> evalMapper(e,
			ServiceFallsDto.class);

	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<ServiceFallsMaster>, List<ServiceFallsDto>> TO_SERVICEFALLMASTER_DTOS = e -> e.stream()
			.map(dm -> TO_SERVICEFALLMASTER_DTO.apply(dm).get()).collect(Collectors.toList());

}
