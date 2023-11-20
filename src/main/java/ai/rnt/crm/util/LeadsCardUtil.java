package ai.rnt.crm.util;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.exception.CRMException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanket Wakankar
 * @version 1.0
 * @since 08/09/2023.
 *
 */
@Slf4j
public class LeadsCardUtil {

	private LeadsCardUtil() {

	}
	 private static final Map<String,String> fieldMap=new HashMap<>();
	 static{
			fieldMap.put("Lead Name", "firstName+lastName");
			fieldMap.put("Topic", "topic");
			fieldMap.put("Company Name", "companyMaster.companyName");
			fieldMap.put("Designation", "designation");
			fieldMap.put("Budget Amount", "budgetAmount");
			fieldMap.put("Service Falls Into", "serviceFallsMaster.serviceName");
			fieldMap.put("Lead Source", "leadSourceMaster.sourceName");
			fieldMap.put("Lead Assign Username", "employee.firstName+employee.lastName");
	}


	public static String shortName(String fName, String lName) {
		try {
			Pattern pattern = Pattern.compile("^.");
			Matcher firstNameMatcher = pattern.matcher(fName);
			Matcher lastNameMatcher = pattern.matcher(lName);
			if (firstNameMatcher.find() && lastNameMatcher.find())
				return nonNull(firstNameMatcher.group() + lastNameMatcher.group())
						? (firstNameMatcher.group() + lastNameMatcher.group()).toUpperCase()
						: null;
			return null;
		} catch (Exception e) {
			log.error("Got exception while concating the fname and lname..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public static String shortName(String fullName) {
		Matcher firstNameMatcher = null;
		Matcher lastNameMatcher = null;
		Pattern pattern = Pattern.compile("^.");
		try {
			if (Objects.nonNull(fullName) && fullName.trim().contains(" ")) {
				String[] result = fullName.split(" ");
				firstNameMatcher = pattern.matcher(result[0]);
				lastNameMatcher = pattern.matcher(result[1]);
				if (firstNameMatcher.find() && lastNameMatcher.find())
					return (nonNull(firstNameMatcher.group() + lastNameMatcher.group())
							? firstNameMatcher.group() + lastNameMatcher.group().toUpperCase()
							: null);
			} else {
				firstNameMatcher = pattern.matcher(fullName.charAt(0) + "");
				if (firstNameMatcher.find())
					return (nonNull(firstNameMatcher.group()) ? firstNameMatcher.group().toUpperCase() : null);
			}
			return null;
		} catch (Exception e) {
			log.error("Got exception while concating the firstName and lastName..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	/**
	 * This function return true if it contains the Leads Object.
	 * 
	 * @since version 1.0
	 */
	public static boolean checkDuplicateLead(List<Leads> allLeads, Leads newLead) {
		boolean flag = false;
		try {
			if (isNull(allLeads) || allLeads.isEmpty())
				return false;
			for (Leads leads : allLeads) {
				if (leads.getFirstName().equals(newLead.getFirstName())
						&& leads.getLastName().equals(newLead.getLastName())
						&& ((isNull(leads.getBudgetAmount()) && isNull(newLead.getBudgetAmount()))
								|| leads.getBudgetAmount().equals(newLead.getBudgetAmount()))
						&& ((isNull(leads.getBusinessCard()) && isNull(newLead.getBusinessCard()))
								|| leads.getBusinessCard().equals(newLead.getBusinessCard()))
						&& ((isNull(leads.getCompanyWebsite()) && isNull(newLead.getCompanyWebsite()))
								|| leads.getCompanyWebsite().equals(newLead.getCompanyWebsite()))
						&& ((isNull(leads.getCompanyMaster()) && isNull(newLead.getCompanyMaster()))
								|| (isNull(leads.getCompanyMaster().getCompanyId())
										&& isNull(newLead.getCompanyMaster().getCompanyId()))
								|| leads.getCompanyMaster().getCompanyId()
										.equals(newLead.getCompanyMaster().getCompanyId()))
						&& ((isNull(leads.getCustomerNeed()) && isNull(newLead.getCustomerNeed()))
								|| leads.getCustomerNeed().equals(newLead.getCustomerNeed()))
						&& ((isNull(leads.getDesignation()) && isNull(newLead.getDesignation()))
								|| leads.getDesignation().equals(newLead.getDesignation()))
						&& leads.getEmail().equals(newLead.getEmail())
						&& leads.getPhoneNumber().equals(newLead.getPhoneNumber())
						&& ((isNull(leads.getProposedSolution()) && isNull(newLead.getProposedSolution()))
								|| leads.getProposedSolution().equals(newLead.getProposedSolution()))
						&& ((isNull(leads.getTopic()) && isNull(newLead.getTopic()))
								|| leads.getTopic().equals(newLead.getTopic()))
						&& (isNull(leads.getLeadSourceMaster()) && isNull(newLead.getLeadSourceMaster())
								|| (isNull(leads.getLeadSourceMaster().getLeadSourceId())
										&& isNull(newLead.getLeadSourceMaster().getLeadSourceId()))
								|| leads.getLeadSourceMaster().getLeadSourceId()
										.equals(newLead.getLeadSourceMaster().getLeadSourceId()))
						&& (isNull(leads.getServiceFallsMaster()) && isNull(newLead.getServiceFallsMaster())
								|| (isNull(leads.getServiceFallsMaster().getServiceFallsId())
										&& isNull(newLead.getServiceFallsMaster().getServiceFallsId()))
								|| leads.getServiceFallsMaster().getServiceFallsId()
										.equals(newLead.getServiceFallsMaster().getServiceFallsId()))

				) {
					flag = true;
					break;
				}
			}
			return flag;
		} catch (Exception e) {
			log.info("Got Exception while checking the DuplicateLead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
	
	public static String leadCardFieldName(String field) {
		if(fieldMap.containsKey(field))
		  return fieldMap.get(field);
	return field;
	}
	
	Function<String, Integer> func = x -> x.length();
	public static LeadsCardDto sendDataToLeadCardDto(Leads lead, String primayField, String secondaryField) {
		LeadsCardDto leadsCardDto = new LeadsCardDto();
		leadsCardDto.setLeadId(lead.getLeadId());
		leadsCardDto.setDisqualifyAs(lead.getDisqualifyAs());
		leadsCardDto.setStatus(lead.getStatus());
		leadsCardDto.setImportant(lead.getImportant());
		switch(primayField) {
		case "Lead Name":
			leadsCardDto.setPrimaryField(lead.getFirstName() + " " + lead.getLastName());
			leadsCardDto.setShortName(LeadsCardUtil.shortName(lead.getFirstName(), lead.getLastName()));
			break;
		case "Company Name":
			leadsCardDto.setPrimaryField(lead.getCompanyMaster().getCompanyName());
			leadsCardDto.setShortName(LeadsCardUtil.shortName(lead.getCompanyMaster().getCompanyName()));
			break;
		default:
			leadsCardDto.setPrimaryField(lead.getFirstName() + " " + lead.getLastName());
			leadsCardDto.setShortName(LeadsCardUtil.shortName(lead.getFirstName(), lead.getLastName()));
			break;
		}
		
		switch (secondaryField) {
		case "Lead Name":
			leadsCardDto.setSecondaryField(lead.getFirstName() + " " + lead.getLastName());
			break;
		case "Topic":
			leadsCardDto.setSecondaryField(lead.getTopic());
			break;
		case "Company Name":
			leadsCardDto.setSecondaryField(lead.getCompanyMaster().getCompanyName());
			break;
		case "Designation":
			leadsCardDto.setSecondaryField(lead.getDesignation());
			break;
		case "Budget Amount":
			leadsCardDto.setSecondaryField(lead.getBudgetAmount());
			break;
		case "Service Falls Into":
			leadsCardDto.setSecondaryField(lead.getServiceFallsMaster().getServiceName());
			break;
		case "Lead Source":
			leadsCardDto.setSecondaryField(lead.getLeadSourceMaster().getSourceName());
			break;
		case "Lead Assign Username":
			leadsCardDto.setSecondaryField(lead.getEmployee().getFirstName() + " " + lead.getEmployee().getLastName());
			break;
		default:
			leadsCardDto.setSecondaryField(lead.getTopic());
			break;
		}
		return leadsCardDto;
	}
}