package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.LeadSourceDto;
import ai.rnt.crm.entity.LeadSourceMaster;

public class LeadSourceDtoMapper {
	LeadSourceDtoMapper(){}
	
	/**
	 * This function will convert LeadDto into optional Leads Entity. <b>This
	 * function will return null if passed LeadDto is null</b> <br>
	 * <b>Param</b> LeadDto <br>
	 * <b>Return</b> Leads
	 * 
	 * @since 04-09-2023
	 * @version 1.0
	 */
	public static final Function<LeadSourceDto, Optional<LeadSourceMaster>> TO_LEAD_SOURCE = e ->evalMapper(e, LeadSourceMaster.class);
	
	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<LeadSourceDto>, List<LeadSourceMaster>> TO_LEAD_SOURCES = e -> e.stream()
			.map(dm -> TO_LEAD_SOURCE.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert Leads Entity into optional LeadDTO . <b>This
	 * function will return null if passed LeadDto is null</b> <br>
	 * <b>Param</b> Leads <br>
	 * <b>Return</b> LeadDto
	 * 
	 * @since 04-09-2023
	 * @Version 1.0
	 */
	public static final Function<LeadSourceMaster, Optional<LeadSourceDto>> TO_LEAD_SOURCE_DTO = e ->evalMapper(e, LeadSourceDto.class);

	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<LeadSourceMaster>, List<LeadSourceDto>> TO_LEAD_SOURCE_DTOS = e -> e.stream()
			.map(dm -> TO_LEAD_SOURCE_DTO.apply(dm).get()).collect(Collectors.toList());
}
