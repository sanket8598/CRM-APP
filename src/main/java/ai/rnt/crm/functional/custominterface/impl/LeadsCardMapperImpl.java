package ai.rnt.crm.functional.custominterface.impl;

import static ai.rnt.crm.constants.LeadEntityFieldConstant.BUDGET_AMOUNT;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.COMPANY_NAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.DESIGNATION;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.DOMAIN;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_ASSIGN_USERNAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_NAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_SOURCE;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.SERVICE_FALLS_INTO;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.TOPIC;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDate;
import static ai.rnt.crm.util.LeadsCardUtil.shortName;
import static java.util.Objects.nonNull;

import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.functional.custominterface.LeadsCardMapper;
import ai.rnt.crm.functional.custominterface.PrimaryFieldMapper;
import ai.rnt.crm.functional.custominterface.SecondaryFieldMapper;
import ai.rnt.crm.util.LeadsCardUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeadsCardMapperImpl implements LeadsCardMapper {

	@Override
	public LeadsCardDto mapLeadToLeadsCardDto(Leads lead, String primaryField, String secondaryField,Contacts contacts) {
		log.info("inside mapLeadToLeadsCardDto method... {} {} {} {}",lead,primaryField,secondaryField,contacts);
		LeadsCardDto leadsCardDto = new LeadsCardDto();
		leadsCardDto.setLeadId(lead.getLeadId());
		leadsCardDto.setDisqualifyAs(lead.getDisqualifyAs());
		leadsCardDto.setStatus(lead.getStatus());
		leadsCardDto.setImportant(lead.getImportant());
		leadsCardDto.setCreatedDate(convertDate(lead.getCreatedDate()));

		PrimaryFieldMapper primaryFieldMapper = getPrimaryFieldMapper(primaryField);
		SecondaryFieldMapper secondaryFieldMapper = getSecondaryFieldMapper(secondaryField);

		primaryFieldMapper.apply(lead, contacts, leadsCardDto);
		secondaryFieldMapper.apply(lead, contacts, leadsCardDto);
		return leadsCardDto;
	}

	private PrimaryFieldMapper getPrimaryFieldMapper(String primaryField) {
		log.info("inside getPrimaryFieldMapper method...{} ",primaryField);
		switch (primaryField) {
		case LEAD_NAME:
			return (lead, contacts, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(
						nonNull(contacts) ? contacts.getFirstName() + " " + contacts.getLastName() : "");
				leadsCardDto.setShortName(shortName(nonNull(contacts) ? contacts.getFirstName() : null,
						nonNull(contacts) ? contacts.getLastName() : null));
			};
		case COMPANY_NAME:
			return (lead, contacts, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(nonNull(contacts) && nonNull(contacts.getCompanyMaster())
						? contacts.getCompanyMaster().getCompanyName()
						: null);
				leadsCardDto.setShortName(nonNull(contacts) && nonNull(contacts.getCompanyMaster())
						&& nonNull(contacts.getCompanyMaster().getCompanyName())
								? LeadsCardUtil.shortName(contacts.getCompanyMaster().getCompanyName())
								: null);
			};
		default:
			return (lead, contacts, leadsCardDto) -> {
				leadsCardDto.setPrimaryField(
						nonNull(contacts) ? contacts.getFirstName() + " " + contacts.getLastName() : "");
				leadsCardDto.setShortName(LeadsCardUtil.shortName(nonNull(contacts) ? contacts.getFirstName() : null,
						nonNull(contacts) ? contacts.getLastName() : null));
			};
		}
	}

	private SecondaryFieldMapper getSecondaryFieldMapper(String secondaryField) {
		log.info("inside getSecondaryFieldMapper method...{} ",secondaryField);
		switch (secondaryField) {
		case LEAD_NAME:
			return (lead, contacts, leadsCardDto) -> {
				leadsCardDto.setSecondaryField(
						nonNull(contacts) ? contacts.getFirstName() + " " + contacts.getLastName() : "");
				leadsCardDto.setShortName(shortName(nonNull(contacts) ? contacts.getFirstName() : null,
						nonNull(contacts) ? contacts.getLastName() : null));
			};
		case TOPIC:
			return (lead, contacts, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getTopic());
		case COMPANY_NAME:
			return (lead, contacts, leadsCardDto) -> {
				leadsCardDto.setSecondaryField(nonNull(contacts) && nonNull(contacts.getCompanyMaster())
						? contacts.getCompanyMaster().getCompanyName()
						: null);
				leadsCardDto.setShortName(nonNull(contacts) && nonNull(contacts.getCompanyMaster())
						&& nonNull(contacts.getCompanyMaster().getCompanyName())
								? LeadsCardUtil.shortName(contacts.getCompanyMaster().getCompanyName())
								: null);
			};
		case DESIGNATION:
			return (lead, contacts, leadsCardDto) -> leadsCardDto.setSecondaryField(contacts.getDesignation());

		case BUDGET_AMOUNT:
			return (lead, contacts, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getBudgetAmount());
		case SERVICE_FALLS_INTO:
			return (lead, contacts, leadsCardDto) -> leadsCardDto.setSecondaryField(
					nonNull(lead.getServiceFallsMaster()) ? lead.getServiceFallsMaster().getServiceName() : null);
		case LEAD_SOURCE:
			return (lead, contacts, leadsCardDto) -> leadsCardDto.setSecondaryField(
					nonNull(lead.getLeadSourceMaster()) ? lead.getLeadSourceMaster().getSourceName() : null);
		case LEAD_ASSIGN_USERNAME:
			return (lead, contacts,
					leadsCardDto) -> leadsCardDto.setSecondaryField(nonNull(lead.getEmployee())
							? lead.getEmployee().getFirstName() + " " + lead.getEmployee().getLastName()
							: null);
		case DOMAIN:
			return (lead, contacts, leadsCardDto) -> leadsCardDto.setSecondaryField(
					nonNull(lead.getDomainMaster()) ? lead.getDomainMaster().getDomainName() : null);
		default:
			return (lead, contacts, leadsCardDto) -> leadsCardDto.setSecondaryField(lead.getTopic());
		}
	}

}
