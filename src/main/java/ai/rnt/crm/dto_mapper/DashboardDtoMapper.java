package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.dto.opportunity.mapper.OpportunityDtoMapper.TO_DASHBOARD_OPPORTUNITY_DTO;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DASHBOARD_LEADDTO;
import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static ai.rnt.crm.util.LeadsCardUtil.shortName;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import ai.rnt.crm.dto.DashboardCardDto;
import ai.rnt.crm.dto.OptyMainDashboardDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Opportunity;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class DashboardDtoMapper {

	public static final Function<Leads, Optional<DashboardCardDto>> TO_DASHBOARD_DTO = e -> {
		Optional<DashboardCardDto> dashBoardDto = evalMapper(e, DashboardCardDto.class);
		dashBoardDto.ifPresent(dashBoardCard -> {
			TO_DASHBOARD_LEADDTO.apply(e).ifPresent(lead -> {
				dashBoardCard.setShortName(
						shortName(lead.getPrimaryContact().getFirstName(), lead.getPrimaryContact().getLastName()));
				dashBoardCard.setLeads(lead);
			});
		});
		return dashBoardDto;
	};

	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Leads>, List<DashboardCardDto>> TO_DASHBOARD_DTOS = e -> e.stream()
			.map(dm -> TO_DASHBOARD_DTO.apply(dm).get()).collect(toList());

	public static final Function<Opportunity, Optional<OptyMainDashboardDto>> TO_OPTY_MAIN_DASHBOARD_DTO = e -> {
		Optional<OptyMainDashboardDto> optyDashBoardDto = evalMapper(e, OptyMainDashboardDto.class);
		optyDashBoardDto.ifPresent(optyDashBoard -> {
			TO_DASHBOARD_OPPORTUNITY_DTO.apply(e).ifPresent(opty -> {
				optyDashBoard.setShortName(shortName(opty.getLeadDashboardDto().getPrimaryContact().getFirstName(),
						opty.getLeadDashboardDto().getPrimaryContact().getLastName()));
				optyDashBoard.setOpty(opty);
			});
		});
		return optyDashBoardDto;
	};

	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Opportunity>, List<OptyMainDashboardDto>> TO_OPTY_MAIN_DASHBOARD_DTOS = e -> e
			.stream().map(dm -> TO_OPTY_MAIN_DASHBOARD_DTO.apply(dm).get()).collect(toList());

}
