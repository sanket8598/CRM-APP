package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalDto {

	private Integer propId;

	private String genPropId;

	private String signature;

	@NotNull(message = "Effective From date should not be null!!")
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate effectiveFrom;

	@NotNull(message = "Effective To date should not be null!!")
	@FutureOrPresent(message = "Date must not be smaller than today's date!!")
	private LocalDate effectiveTo;
}
