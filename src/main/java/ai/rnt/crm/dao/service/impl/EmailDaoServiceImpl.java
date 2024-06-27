package ai.rnt.crm.dao.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmailDaoServiceImpl implements EmailDaoService {

	private final EmailRepository emailRepository;

	@Override
	public Email email(Email email) {
		log.info("inside the save email method...{}");
		return emailRepository.save(email);
	}

	@Override
	public List<Email> getEmailByLeadId(Integer leadId) {
		log.info("inside the getEmailByLeadId method...{}" + leadId);
		return emailRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Email findById(Integer addMailId) {
		log.info("inside the findById method...{}" + addMailId);
		return emailRepository.findById(addMailId)
				.orElseThrow(() -> new ResourceNotFoundException("AddEmail", "addMailId", addMailId));
	}

	@Override
	public Boolean emailPresentForLeadLeadId(Integer addMailId, Integer leadId) {
		log.info("inside the emailPresentForLeadLeadId method...{}{}" + addMailId, leadId);
		return emailRepository.existsByMailIdAndLeadLeadId(addMailId, leadId);
	}

	@Override
	public List<Email> isScheduledEmails(LocalDate todayAsDate, String time) {
		log.info("inside the isScheduledEmails method...{}{}" + todayAsDate, time);
		return emailRepository.findByScheduledOnAndScheduledAtAndScheduled(todayAsDate, time, true);
	}

	@Override
	public List<Email> getEmailByLeadIdAndIsOpportunity(Integer leadId) {
		log.info("inside the getEmailByLeadIdAndIsOpportunity method...{}" + leadId);
		return emailRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, false);
	}

	@Override
	public String findPasswordByMailId(String userName) {
		log.info("inside the findPasswordByMailId method...{}" + userName);
		return emailRepository.findPasswordByMailId(userName);
	}

	@Override
	public List<Email> getAllLeadEmails(boolean isOpportunity) {
		log.info("inside the getAllLeadEmails method...{}" + isOpportunity);
		return emailRepository.findByIsOpportunityOrderByCreatedDateDesc(isOpportunity);
	}
}
