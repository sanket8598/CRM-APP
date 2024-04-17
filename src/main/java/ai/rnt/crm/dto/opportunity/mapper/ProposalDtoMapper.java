package ai.rnt.crm.dto.opportunity.mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.entity.Proposal;
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
}
