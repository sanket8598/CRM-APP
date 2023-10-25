package ai.rnt.crm.util;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static String shortName(String fName, String lName) {
		try {
			Pattern pattern = Pattern.compile("^.");
			Matcher firstNameMatcher = pattern.matcher(fName);
			Matcher lastNameMatcher = pattern.matcher(lName);
			if (firstNameMatcher.find() && lastNameMatcher.find())
				return (firstNameMatcher.group() + lastNameMatcher.group()).toUpperCase();
			return null;
		} catch (Exception e) {
			log.error("Got exception while concating the fname and lname");
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
					return (firstNameMatcher.group() + lastNameMatcher.group()).toUpperCase();
			} else {
				firstNameMatcher = pattern.matcher(fullName.charAt(0) + "");
				if (firstNameMatcher.find())
					return (firstNameMatcher.group()).toUpperCase();
			}
			return null;
		} catch (Exception e) {
			log.error("Got exception while concating the firstName and lastName");
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
		if (isNull(allLeads) || allLeads.isEmpty())
			return false;
		for (Leads leads : allLeads) {
			if (leads.getFirstName().equals(newLead.getFirstName()) && leads.getLastName().equals(newLead.getLastName())
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
					&& leads.getEmail().equals(newLead.getEmail()) && leads.getPhoneNumber().equals(newLead.getPhoneNumber())
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
	}
}