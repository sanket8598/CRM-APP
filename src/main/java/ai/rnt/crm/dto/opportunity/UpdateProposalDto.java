package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProposalDto {

	private Integer propId;

	@NotNull(message = "Effective From date should not be null!!")
	private LocalDate effectiveFrom;

	private String propDescription;

	@NotNull(message = "Effective To date should not be null!!")
	private LocalDate effectiveTo;

	private List<ProposalServicesDto> proposalServices = new ArrayList<>();
}
