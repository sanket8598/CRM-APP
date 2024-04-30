package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProposalDto {

	private Integer propId;

	private String genPropId;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate effectiveFrom;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate effectiveTo;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDateTime createdOn;

	private String propDescription;

	private String subTotal;

	private String finalAmount;

	private Integer discount;

	private List<ProposalServicesDto> proposalServices = new ArrayList<>();

	private OpportunityDto opportunity;
}
