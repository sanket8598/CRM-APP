package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalDto {

	private Integer propId;

	private String genPropId;

	private LocalDate effectiveFrom;

	private LocalDate effectiveTo;
}
