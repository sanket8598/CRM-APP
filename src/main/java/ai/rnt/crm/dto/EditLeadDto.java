package ai.rnt.crm.dto;

import java.util.ArrayList;
import java.util.List;

import ai.rnt.crm.util.LeadsCardUtil;
import lombok.Data;

@Data
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
		return LeadsCardUtil.shortName(primaryContact.getFirstName(), primaryContact.getLastName());
	}

	public String getFullName() {
		return primaryContact.getFirstName()+" "+primaryContact.getLastName();
	}

	private List<ContactDto> contacts = new ArrayList<>();
	
}
