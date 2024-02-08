package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTO;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDate;
import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.LeadDashboardDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Opportunity;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class OpportunityDtoMapper {

	public static final Function<Opportunity, Optional<LeadDashboardDto>> TO_DASHBOARD_OPPORTUNITY_DTO = e -> {
		Optional<LeadDashboardDto> opportunityDashboardDto = evalMapper(e, LeadDashboardDto.class);
		opportunityDashboardDto.ifPresent(l -> {
			l.setCreatedOn(convertDate(e.getCreatedDate()));
			l.setPrimaryContact(
					TO_CONTACT_DTO.apply(e.getContacts().stream().filter(Contacts::getPrimary).findFirst().orElse(null))
							.orElse(null));
		});
		return opportunityDashboardDto;
	};

	public static final Function<Collection<Opportunity>, List<LeadDashboardDto>> TO_DASHBOARD_OPPORTUNITY_DTOS = e -> e
			.stream().map(dm -> TO_DASHBOARD_OPPORTUNITY_DTO.apply(dm).get()).collect(Collectors.toList());

}
