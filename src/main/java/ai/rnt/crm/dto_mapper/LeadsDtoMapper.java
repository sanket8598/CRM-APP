package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICEFALLMASTER;
import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.NewLeadDto;
import ai.rnt.crm.entity.Leads;

public class LeadsDtoMapper {

	LeadsDtoMapper() {
	}

	/**
	 * This function will convert LeadDto into optional Leads Entity. <b>This
	 * function will return null if passed LeadDto is null</b> <br>
	 * <b>Param</b> LeadDto <br>
	 * <b>Return</b> Leads
	 * 
	 * @since 04-09-2023
	 * @version 1.0
	 */
	public static final Function<LeadDto, Optional<Leads>> TO_LEAD = e ->{
		Leads leads = evalMapper(e, Leads.class).get();
		TO_COMPANY.apply(e.getCompanyMaster()).ifPresent(leads::setCompanyMaster);
		TO_LEAD_SOURCE.apply(e.getLeadSourceMaster()).ifPresent(leads::setLeadSourceMaster);
		TO_SERVICEFALLMASTER.apply(e.getServiceFallsMaster()).ifPresent(leads::setServiceFallsMaster);
		return Optional.of(leads);
	};
	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<LeadDto>, List<Leads>> TO_LEADS = e -> e.stream()
			.map(dm -> TO_LEAD.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert Leads Entity into optional LeadDTO . <b>This
	 * function will return null if passed LeadDto is null</b> <br>
	 * <b>Param</b> Leads <br>
	 * <b>Return</b> LeadDto
	 * 
	 * @since 04-09-2023
	 * @Version 1.0
	 */
	public static final Function<Leads, Optional<LeadDto>> TO_LEAD_DTO = e ->evalMapper(e, LeadDto.class);

	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Leads>, List<LeadDto>> TO_LEAD_DTOS = e -> e.stream()
			.map(dm -> TO_LEAD_DTO.apply(dm).get()).collect(Collectors.toList());
	
	public static final Function<NewLeadDto, Optional<Leads>> TO_NEWLEAD = e -> evalMapper(e, Leads.class);

}
