package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProposalDto {

	private Integer propId;

	private LocalDate effectiveFrom;

	private String propDescription;

	private LocalDate effectiveTo;

	private List<ProposalServicesDto> proposalServices = new ArrayList<>();
}
