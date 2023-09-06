package ai.rnt.crm.util;

import static java.util.Objects.nonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import ai.rnt.crm.dto.LeadDto;

public class LeadUtil {

	public static LeadDto toLeadDto(HttpServletRequest req) {
		LeadDto dto = new LeadDto();
		dto.setFirstName(req.getParameter("firstName"));
		dto.setLastName(req.getParameter("lastName"));
		String phoneNum = req.getParameter("phoneNumber");
		dto.setPhoneNumber(getDigits(phoneNum));
		String assignTo = req.getParameter("assignTo");
		dto.setAssignTo(getDigits(assignTo));
		String budgetAmount = req.getParameter("budgetAmount");
		dto.setBudgetAmount(nonNull(phoneNum) ? Float.parseFloat(budgetAmount) : null);
		dto.setEmail(req.getParameter("email"));
		dto.setTopic(req.getParameter("topic"));
		String companyId = req.getParameter("companyId");
		String serviceFallsId = req.getParameter("serviceFallsId");
		String leadSourceId = req.getParameter("leadSourceId");
		dto.setCompanyId(getDigits(companyId));
		dto.setServiceFallsId(getDigits(serviceFallsId));
		dto.setLeadSourceId(getDigits(leadSourceId));
		return dto;

	}

	public static Integer getDigits(String input) {
		Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
		// Create a Matcher
		Matcher matcher = pattern.matcher(input);
		// Find all matched numbers
		String number = null;
		while (matcher.find()) {
			number = matcher.group();
		}
		return nonNull(number) && StringUtils.isNumeric(number) ? Integer.parseInt(number) : null;
	}

	public static LeadDto createLeadDto(String firstName, String lastName, Integer phoneNumber, Integer assignTo,
			Float budgetAmount, String email, String topic, Integer companyId, Integer serviceFallsId,
			Integer leadSourceId) {
		LeadDto dto = new LeadDto();
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setPhoneNumber(phoneNumber);
		dto.setAssignTo(assignTo);
		dto.setBudgetAmount(budgetAmount);
		dto.setEmail(email);
		dto.setTopic(topic);
		dto.setCompanyId(companyId);
		dto.setServiceFallsId(serviceFallsId);
		dto.setLeadSourceId(leadSourceId);
		return dto;

	}

}
