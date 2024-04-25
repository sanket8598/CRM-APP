package ai.rnt.crm.dto.opportunity.mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.opportunity.EditProposalDto;
import ai.rnt.crm.dto.opportunity.GetProposalsDto;
import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.entity.ProposalServices;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ProposalDtoMapper {

	/**
	 * This function will convert ProposalDto into optional Proposal Entity. <b>This
	 * function will return null if passed ProposalDto is null</b> <br>
	 * <b>Param</b> ProposalDto <br>
	 * <b>Return</b> Proposal
	 * 
	 * @since 16-04-2024
	 * @version 1.0
	 */
	public static final Function<ProposalDto, Optional<Proposal>> TO_PROPOSAL = e -> evalMapper(e, Proposal.class);
	/**
	 * @since 16-04-2024
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<ProposalDto>, List<Proposal>> TO_PROPOSALS = e -> e.stream()
			.map(dm -> TO_PROPOSAL.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Proposal, Optional<GetProposalsDto>> TO_PROPOSAL_DTO = e -> {
		Optional<GetProposalsDto> evalMapper2 = evalMapper(e, GetProposalsDto.class);
		evalMapper2.ifPresent(prop -> prop.setOptyName(e.getOpportunity().getTopic()));
		return evalMapper2;
	};

	public static final Function<Collection<Proposal>, List<GetProposalsDto>> TO_PROPOSAL_DTOS = e -> e.stream()
			.map(dm -> TO_PROPOSAL_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<ProposalServicesDto, Optional<ProposalServices>> TO_PROPOSAL_SERVICE = e -> evalMapper(
			e, ProposalServices.class);
	public static final Function<ProposalServices, Optional<ProposalServicesDto>> TO_PROPOSAL_SERVICE_DTO = e -> evalMapper(
			e, ProposalServicesDto.class);
	/**
	 * @since 22-04-2024
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<ProposalServicesDto>, List<ProposalServices>> TO_PROPOSAL_SERVICES = e -> e
			.stream().map(dm -> TO_PROPOSAL_SERVICE.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Proposal, Optional<EditProposalDto>> TO_EDIT_PROPOSAL_DTO = e -> evalMapper(e,
			EditProposalDto.class);
	public static final Function<Collection<Proposal>, List<EditProposalDto>> TO_EDIT_PROPOSAL_DTOS = e -> e.stream()
			.map(dm -> TO_EDIT_PROPOSAL_DTO.apply(dm).get()).collect(Collectors.toList());

}
