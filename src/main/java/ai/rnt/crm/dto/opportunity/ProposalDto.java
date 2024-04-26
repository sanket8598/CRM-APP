package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalDto {

	private Integer propId;

	private String genPropId;

	@NotNull(message = "Effective From date should not be null!!")
	private LocalDate effectiveFrom;

	@NotNull(message = "Effective To date should not be null!!")
	private LocalDate effectiveTo;
}
