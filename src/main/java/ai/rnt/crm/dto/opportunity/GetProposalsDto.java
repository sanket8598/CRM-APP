package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProposalsDto {

	private Integer propId;

	private String genPropId;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate effectiveFrom;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate effectiveTo;

	private String optyName;
}
