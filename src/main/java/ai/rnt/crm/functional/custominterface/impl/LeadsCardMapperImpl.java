package ai.rnt.crm.functional.custominterface.impl;

import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.functional.custominterface.LeadsCardMapper;
import ai.rnt.crm.functional.custominterface.PrimaryFieldMapper;
import ai.rnt.crm.functional.custominterface.SecondaryFieldMapper;
import ai.rnt.crm.util.LeadsCardUtil;

public class LeadsCardMapperImpl implements LeadsCardMapper {

	@Override
	public LeadsCardDto mapLeadToLeadsCardDto(Leads lead, String primaryField, String secondaryField) {
		LeadsCardDto leadsCardDto = new LeadsCardDto();
		leadsCardDto.setLeadId(lead.getLeadId());
		leadsCardDto.setDisqualifyAs(lead.getDisqualifyAs());
		leadsCardDto.setStatus(lead.getStatus());
		leadsCardDto.setImportant(lead.getImportant());
		PrimaryFieldMapper primaryFieldMapper = getPrimaryFieldMapper(primaryField);
		SecondaryFieldMapper secondaryFieldMapper = getSecondaryFieldMapper(secondaryField);

		primaryFieldMapper.apply(lead, leadsCardDto);
		secondaryFieldMapper.apply(lead, leadsCardDto);

		return leadsCardDto;
	}

	private PrimaryFieldMapper getPrimaryFieldMapper(String primaryField) {
		switch (primaryField) {
		case "Lead Name":
			return (lead, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(lead.getFirstName() + " " + lead.getLastName());
				leadsCardDto.setShortName(LeadsCardUtil.shortName(lead.getFirstName(), lead.getLastName()));
			};
		case "Company Name":
			return (lead, leadsCardDto) -> leadsCardDto.setPrimaryField(lead.getCompanyMaster().getCompanyName());
		default:
			return (lead, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(lead.getFirstName() + " " + lead.getLastName());
				leadsCardDto.setShortName(LeadsCardUtil.shortName(lead.getFirstName(), lead.getLastName()));
			};
		}
	}

	private SecondaryFieldMapper getSecondaryFieldMapper(String secondaryField) {
		switch (secondaryField) {
		case "Lead Name":
			return (lead, leadsCardDto) -> leadsCardDto
					.setSecondaryField(lead.getFirstName() + " " + lead.getLastName());
		case "Topic":
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getTopic());
		case "Company Name":
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getCompanyMaster().getCompanyName());
		case "Designation":
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getDesignation());
		case "Budget Amount":
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getBudgetAmount());
		case "Service Falls Into":
			return (lead, leadsCardDto) -> leadsCardDto
					.setSecondaryField(lead.getServiceFallsMaster().getServiceName());
		case "Lead Source":
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getLeadSourceMaster().getSourceName());
		case "Lead Assign Username":
			return (lead, leadsCardDto) -> leadsCardDto
					.setSecondaryField(lead.getEmployee().getFirstName() + " " + lead.getEmployee().getLastName());
		default:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getTopic());
		}
	}

}
