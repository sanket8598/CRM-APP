package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.dto.LeadDashboardDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.util.ConvertDateFormatUtil;
import ai.rnt.crm.util.LeadsCardUtil;

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
	public static final Function<LeadDto, Optional<Leads>> TO_LEAD = e -> evalMapper(e, Leads.class);
		;
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
	
	
	public static final Function<Leads, Optional<LeadDashboardDto>> TO_DASHBOARD_LEADDTO = e -> {
		Optional<LeadDashboardDto> leadDashboardDto = evalMapper(e, LeadDashboardDto.class);
		leadDashboardDto.ifPresent(l->l.setCreatedOn(ConvertDateFormatUtil.convertDate(e.getCreatedDate())));
		return leadDashboardDto;
	};

	public static final Function<Collection<Leads>, List<LeadDashboardDto>> TO_DASHBOARD_LEADDTOS = e -> e.stream()
			.map(dm -> TO_DASHBOARD_LEADDTO.apply(dm).get()).collect(Collectors.toList());
	
	
	public static final Function<Leads, Optional<LeadsCardDto>> TO_DASHBOARD_CARDS_LEADDTO = e -> {
		Optional<LeadsCardDto> leadDashboardDto = evalMapper(e, LeadsCardDto.class);
		leadDashboardDto.ifPresent(l->l.setShortName(LeadsCardUtil.shortName(e.getFirstName(), e.getLastName())));
		return leadDashboardDto;
	};

	public static final Function<Collection<Leads>, List<LeadsCardDto>> TO_DASHBOARD_CARDS_LEADDTOS = e -> e.stream()
			.map(dm -> TO_DASHBOARD_CARDS_LEADDTO.apply(dm).get()).collect(Collectors.toList());
	
	public static final Function<Leads, Optional<EditLeadDto>> TO_EDITLEAD_DTO = e ->evalMapper(e, EditLeadDto.class);
	public static final Function<Collection<Leads>, List<EditLeadDto>> TO_EDITLEAD_DTOS = e -> e.stream().map(dm -> TO_EDITLEAD_DTO.apply(dm).get()).collect(Collectors.toList());
}
