package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTO;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDate;
import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.DescriptionDto;
import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.dto.LeadDashboardDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Description;
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
	public static final Function<LeadDto, Optional<Leads>> TO_LEAD = e -> evalMapper(e, Leads.class);

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
	public static final Function<Leads, Optional<LeadDto>> TO_LEAD_DTO = e -> evalMapper(e, LeadDto.class);

	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Leads>, List<LeadDto>> TO_LEAD_DTOS = e -> e.stream()
			.map(dm -> TO_LEAD_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Leads, Optional<LeadDashboardDto>> TO_DASHBOARD_LEADDTO = e -> {
		Optional<LeadDashboardDto> leadDashboardDto = evalMapper(e, LeadDashboardDto.class);
		leadDashboardDto.ifPresent(l -> {
			l.setCreatedOn(convertDate(e.getCreatedDate()));
			l.setPrimaryContact(
					TO_CONTACT_DTO.apply(e.getContacts().stream().filter(Contacts::getPrimary).findFirst().orElse(null))
							.orElse(null));
		});
		return leadDashboardDto;
	};

	public static final Function<Collection<Leads>, List<LeadDashboardDto>> TO_DASHBOARD_LEADDTOS = e -> e.stream()
			.map(dm -> TO_DASHBOARD_LEADDTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Leads, Optional<LeadsCardDto>> TO_DASHBOARD_CARDS_LEADDTO = e -> evalMapper(e,
			LeadsCardDto.class);

	public static final Function<Collection<Leads>, List<LeadsCardDto>> TO_DASHBOARD_CARDS_LEADDTOS = e -> e.stream()
			.map(dm -> TO_DASHBOARD_CARDS_LEADDTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Leads, Optional<EditLeadDto>> TO_EDITLEAD_DTO = e -> evalMapper(e, EditLeadDto.class);
	public static final Function<Collection<Leads>, List<EditLeadDto>> TO_EDITLEAD_DTOS = e -> e.stream()
			.map(dm -> TO_EDITLEAD_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Leads, Optional<QualifyLeadDto>> TO_QUALIFY_LEAD = e -> evalMapper(e,
			QualifyLeadDto.class);

	public static final Function<DescriptionDto, Optional<Description>> TO_DESCRIPTION = e -> evalMapper(e,
			Description.class);
	/**
	 * @since 06-06-2024
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<DescriptionDto>, List<Description>> TO_DESCRIPTIONS = e -> e.stream()
			.map(dm -> TO_DESCRIPTION.apply(dm).get()).collect(Collectors.toList());
}
