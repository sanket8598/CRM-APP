package ai.rnt.crm.dto.opportunity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProposalDto {

	private Integer propId;

	private String ownerName;

	private String currency;

	private String propDescription;

	private List<ProposalServicesDto> proposalServices = new ArrayList<>();
}
