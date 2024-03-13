package ai.rnt.crm.dto;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

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
