package ai.rnt.crm.functional.custominterface.impl;

import static ai.rnt.crm.constants.LeadEntityFieldConstant.BUDGET_AMOUNT;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.COMPANY_NAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.DESIGNATION;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_ASSIGN_USERNAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_NAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_SOURCE;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.SERVICE_FALLS_INTO;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.TOPIC;
import static java.util.Objects.nonNull;

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
		case LEAD_NAME:
			return (lead, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(lead.getFirstName() + " " + lead.getLastName());
				leadsCardDto.setShortName(LeadsCardUtil.shortName(lead.getFirstName(), lead.getLastName()));
			};
		case COMPANY_NAME:
			return (lead, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(
						nonNull(lead.getCompanyMaster()) ? lead.getCompanyMaster().getCompanyName() : null);
				leadsCardDto.setShortName(
						nonNull(lead.getCompanyMaster()) && nonNull(lead.getCompanyMaster().getCompanyName())
								? LeadsCardUtil.shortName(lead.getCompanyMaster().getCompanyName())
								: null);
			};
		default:
			return (lead, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(lead.getFirstName() + " " + lead.getLastName());
				leadsCardDto.setShortName(LeadsCardUtil.shortName(lead.getFirstName(), lead.getLastName()));
			};
		}
	}

	private SecondaryFieldMapper getSecondaryFieldMapper(String secondaryField) {
		switch (secondaryField) {
		case LEAD_NAME:
			return (lead, leadsCardDto) -> leadsCardDto
					.setSecondaryField(lead.getFirstName() + " " + lead.getLastName());
		case TOPIC:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getTopic());
		case COMPANY_NAME:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(
					nonNull(lead.getCompanyMaster()) ? lead.getCompanyMaster().getCompanyName() : null);
		case DESIGNATION:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getDesignation());
		case BUDGET_AMOUNT:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getBudgetAmount());
		case SERVICE_FALLS_INTO:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(
					nonNull(lead.getServiceFallsMaster()) ? lead.getServiceFallsMaster().getServiceName() : null);
		case LEAD_SOURCE:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(
					nonNull(lead.getLeadSourceMaster()) ? lead.getLeadSourceMaster().getSourceName() : null);
		case LEAD_ASSIGN_USERNAME:
			return (lead,
					leadsCardDto) -> leadsCardDto.setSecondaryField(nonNull(lead.getEmployee())
							? lead.getEmployee().getFirstName() + " " + lead.getEmployee().getLastName()
							: null);
		default:
			return (lead, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getTopic());
		}
	}

}
