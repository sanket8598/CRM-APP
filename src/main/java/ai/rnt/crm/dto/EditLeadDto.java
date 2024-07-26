package ai.rnt.crm.dto;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.dto.opportunity.OpportunityDto;
import ai.rnt.crm.util.LeadsCardUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditLeadDto {

	private Integer leadId;

	private String topic;

	private String budgetAmount;

	private Integer assignTo;

	private String status;

	private String assignBy;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDate assignDate;

	@JsonFormat(pattern = "dd-MMM-yyyy")
	private LocalDateTime createdDate;

	private String customerNeed;

	private String proposedSolution;

	private ServiceFallsDto serviceFallsMaster;

	private LeadSourceDto leadSourceMaster;

	private DomainMasterDto domainMaster;

	private String message;

	private String generatedBy;

	private String leadRequirements;

	private String pseudoName;

	private Integer dropDownAssignTo;

	private ContactDto primaryContact;

	private OpportunityDto opportunity;
	
	private CurrencyDto currency;

	public String getShortName() {
		return nonNull(primaryContact)
				? LeadsCardUtil.shortName(primaryContact.getFirstName(), primaryContact.getLastName())
				: null;
	}

	public String getFullName() {
		return nonNull(primaryContact) ? primaryContact.getFirstName() + " " + primaryContact.getLastName() : null;
	}

	private List<ContactDto> contacts = new ArrayList<>();

}
