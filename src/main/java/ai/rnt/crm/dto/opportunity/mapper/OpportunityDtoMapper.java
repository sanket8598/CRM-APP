package ai.rnt.crm.dto.opportunity.mapper;

import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DASHBOARD_LEADDTO;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDate;
import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.opportunity.AnalysisOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseAsLostOpportunityDto;
import ai.rnt.crm.dto.opportunity.CloseOpportunityDto;
import ai.rnt.crm.dto.opportunity.GraphicalDataDto;
import ai.rnt.crm.dto.opportunity.OpportunityDto;
import ai.rnt.crm.dto.opportunity.OpprtAttachmentDto;
import ai.rnt.crm.dto.opportunity.ProposeOpportunityDto;
import ai.rnt.crm.dto.opportunity.QualifyOpportunityDto;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpprtAttachment;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class OpportunityDtoMapper {

	public static final Function<Opportunity, Optional<OpportunityDto>> TO_DASHBOARD_OPPORTUNITY_DTO = e -> {
		Optional<OpportunityDto> opportunityDashboardDto = evalMapper(e, OpportunityDto.class);
		opportunityDashboardDto.ifPresent(l -> {
			TO_DASHBOARD_LEADDTO.apply(e.getLeads()).ifPresent(l::setLeadDashboardDto);
			l.setCreatedOn(convertDate(e.getCreatedDate()));
		});
		return opportunityDashboardDto;
	};

	public static final Function<Collection<Opportunity>, List<OpportunityDto>> TO_DASHBOARD_OPPORTUNITY_DTOS = e -> e
			.stream().map(dm -> TO_DASHBOARD_OPPORTUNITY_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Opportunity, Optional<GraphicalDataDto>> TO_GRAPHICAL_DATA_DTO = e -> evalMapper(e,
			GraphicalDataDto.class);

	public static final Function<OpportunityDto, Optional<Opportunity>> TO_OPPORTUNITY = e -> evalMapper(e,
			Opportunity.class);

	public static final Function<Opportunity, Optional<OpportunityDto>> TO_OPPORTUNITY_DTO = e -> evalMapper(e,
			OpportunityDto.class);

	public static final Function<Opportunity, Optional<QualifyOpportunityDto>> TO_QUALIFY_OPPORTUNITY_DTO = e -> evalMapper(
			e, QualifyOpportunityDto.class);

	public static final Function<Collection<Opportunity>, List<QualifyOpportunityDto>> TO_QUALIFY_OPPORTUNITY_DTOS = e -> e
			.stream().map(dm -> TO_QUALIFY_OPPORTUNITY_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Opportunity, Optional<AnalysisOpportunityDto>> TO_ANALYSIS_OPPORTUNITY_DTO = e -> evalMapper(
			e, AnalysisOpportunityDto.class);

	public static final Function<OpprtAttachment, Optional<OpprtAttachmentDto>> TO_OPPORTUNITY_ATTACHMENT_DTO = e -> evalMapper(
			e, OpprtAttachmentDto.class);

	public static final Function<OpprtAttachmentDto, Optional<OpprtAttachment>> TO_OPPORTUNITY_ATTACHMENT = e -> evalMapper(
			e, OpprtAttachment.class);

	public static final Function<Collection<OpprtAttachment>, List<OpprtAttachmentDto>> TO_OPPORTUNITY_ATTACHMENT_DTOS = e -> e
			.stream().map(dm -> TO_OPPORTUNITY_ATTACHMENT_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Opportunity, Optional<ProposeOpportunityDto>> TO_PROPOSE_OPPORTUNITY_DTO = e -> evalMapper(
			e, ProposeOpportunityDto.class);

	public static final Function<Opportunity, Optional<CloseOpportunityDto>> TO_CLOSE_OPPORTUNITY_DTO = e -> evalMapper(
			e, CloseOpportunityDto.class);
	
	public static final Function<Opportunity, Optional<CloseAsLostOpportunityDto>> TO_CLOSE_AS_LOST_OPPORTUNITY_DTO = e -> evalMapper(
			e, CloseAsLostOpportunityDto.class);
}
