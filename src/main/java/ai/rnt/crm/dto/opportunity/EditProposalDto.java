package ai.rnt.crm.dto.opportunity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProposalDto {

	private Integer propId;

	private String genPropId;

	private String ownerName;

	private String createdBy;

	private String currency;

	private String propDescription;

	private List<ProposalServicesDto> proposalServices = new ArrayList<>();

	private OpportunityDto opportunity;
}
