package ai.rnt.crm.dao.service;

import java.time.LocalDate;
import java.util.List;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.Email;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 *
 */
public interface EmailDaoService extends CrudService<Email, EmailDto> {

	Email email(Email email);

	List<Email> getEmailByLeadId(Integer leadId);

	Email findById(Integer addMailId);

	Boolean emailPresentForLeadLeadId(Integer addMailId, Integer leadId);

	List<Email> isScheduledEmails(LocalDate todayAsDate, String time);

	List<Email> getEmailByLeadIdAndIsOpportunity(Integer leadId);

	String findPasswordByMailId(String userName);

	List<Email> getAllLeadEmails(boolean isOpportunity);

}
