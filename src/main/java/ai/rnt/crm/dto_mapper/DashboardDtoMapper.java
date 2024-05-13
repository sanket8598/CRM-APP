package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DASHBOARD_LEADDTO;
import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static ai.rnt.crm.util.LeadsCardUtil.shortName;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.DashboardCardDto;
import ai.rnt.crm.entity.Leads;

public class DashboardDtoMapper {

	public static final Function<Leads, Optional<DashboardCardDto>> TO_DASHBOARD_DTO = e ->{
		Optional<DashboardCardDto> dashBoardDto = evalMapper(e,DashboardCardDto.class);
		dashBoardDto.ifPresent(dashBoardCard->{
			TO_DASHBOARD_LEADDTO.apply(e).ifPresent(lead->{
				dashBoardCard.setShortName(shortName(lead.getPrimaryContact().getFirstName(),lead.getPrimaryContact().getLastName()));
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
			.map(dm -> TO_DASHBOARD_DTO.apply(dm).get()).collect(Collectors.toList());

}
