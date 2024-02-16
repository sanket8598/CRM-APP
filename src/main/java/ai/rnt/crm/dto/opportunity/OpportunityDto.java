package ai.rnt.crm.dto.opportunity;

import static ai.rnt.crm.util.LeadsCardUtil.shortName;
import static java.util.Objects.nonNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.LeadDashboardDto;
import lombok.Data;

@Data
public class OpportunityDto {

	private Integer opportunityId;

	private String status;

	private String topic;

	private String pseudoName;

	private String budgetAmount;

	private String customerNeed;

	private String proposedSolution;

	private String message;

	private String generatedBy;

	private Integer dropDownAssignTo;

	@JsonProperty("lead")
	private LeadDashboardDto leadDashboardDto;

	@JsonProperty("assignOpportunity")
	private EmployeeDto employee;

	@JsonProperty("contacts")
	private List<ContactDto> contacts;

	private String createdOn;

	public String getShortName() {
		return nonNull(leadDashboardDto) && nonNull(leadDashboardDto.getPrimaryContact()) ? shortName(
				leadDashboardDto.getPrimaryContact().getFirstName(), leadDashboardDto.getPrimaryContact().getLastName())
				: null;
	}

	public String getFullName() {
		return nonNull(leadDashboardDto) && nonNull(leadDashboardDto.getPrimaryContact())
				? leadDashboardDto.getPrimaryContact().getFirstName() + " "
						+ leadDashboardDto.getPrimaryContact().getLastName()
				: null;
	}
}
