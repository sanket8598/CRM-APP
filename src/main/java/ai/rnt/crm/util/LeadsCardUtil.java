package ai.rnt.crm.util;

import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.compile;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.exception.CRMException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanket Wakankar
 * @version 1.0
 * @since 08/09/2023.
 *
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class LeadsCardUtil {

	public static String shortName(String fName, String lName) {
		try {
			Pattern pattern = compile("^.");
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
		Pattern pattern = compile("^.");
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
		return nonNull(newLead) && allLeads.stream().filter(Objects::nonNull)
				.anyMatch(lead -> Objects.equals(lead.getFirstName(), newLead.getFirstName())
						&& Objects.equals(lead.getLastName(), newLead.getLastName())
						&& Objects.equals(lead.getBudgetAmount(), newLead.getBudgetAmount())
						&& Objects.equals(lead.getBusinessCard(), newLead.getBusinessCard())
						&& Objects.equals(lead.getCompanyWebsite(), newLead.getCompanyWebsite())
						&& Objects.equals(
								nonNull(lead.getCompanyMaster()) ? lead.getCompanyMaster().getCompanyId() : null,
								nonNull(newLead.getCompanyMaster()) ? newLead.getCompanyMaster().getCompanyId() : null)
						&& Objects.equals(lead.getCustomerNeed(), newLead.getCustomerNeed())
						&& Objects.equals(lead.getDesignation(), newLead.getDesignation())
						&& Objects.equals(lead.getEmail(), newLead.getEmail())
						&& Objects.equals(lead.getProposedSolution(), newLead.getProposedSolution())
						&& Objects.equals(lead.getTopic(), newLead.getTopic())
						&& Objects.equals(nonNull(lead.getLeadSourceMaster())
								? lead.getLeadSourceMaster().getLeadSourceId()
								: null,
								nonNull(newLead.getLeadSourceMaster())
										? newLead.getLeadSourceMaster().getLeadSourceId()
										: null)
						&& Objects.equals(
								nonNull(lead.getServiceFallsMaster()) ? lead.getServiceFallsMaster().getServiceFallsId()
										: null,
								nonNull(newLead.getServiceFallsMaster())
										? newLead.getServiceFallsMaster().getServiceFallsId()
										: null)
						&& Objects.equals(lead.getPhoneNumber(), newLead.getPhoneNumber()));
	}

}