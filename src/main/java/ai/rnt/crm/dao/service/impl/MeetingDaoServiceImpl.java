package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.LEAD_ID;
import static ai.rnt.crm.constants.CacheConstant.MEETINGS;
import static ai.rnt.crm.constants.CacheConstant.MEETINGS_BY_LEAD_ID;
import static ai.rnt.crm.constants.CacheConstant.MEETING_TASK;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.repository.MeetingRepository;
import ai.rnt.crm.repository.MeetingTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MeetingDaoServiceImpl implements MeetingDaoService {

	private final MeetingRepository meetingRepository;
	private final MeetingTaskRepository meetingTaskRepository;

	@Override
	@CacheEvict(value = { MEETINGS, MEETINGS_BY_LEAD_ID }, allEntries = true)
	public Meetings addMeeting(Meetings metting) {
		log.info("inside the addMeeting method...");
		return meetingRepository.save(metting);
	}

	@Override
	public Optional<Meetings> getMeetingById(Integer meetingId) {
		log.info("inside the getMeetingById method...{}", meetingId);
		return meetingRepository.findById(meetingId);
	}

	@Override
	@CacheEvict(value = MEETING_TASK, allEntries = true)
	public MeetingTask addMeetingTask(MeetingTask meetingTask) {
		log.info("inside the addMeetingTask method...");
		return meetingTaskRepository.save(meetingTask);
	}

	@Override
	@Cacheable(value = MEETINGS_BY_LEAD_ID, key = LEAD_ID, condition = "#leadId!=null")
	public List<Meetings> getMeetingByLeadId(Integer leadId) {
		log.info("inside the getMeetingByLeadId method...{}", leadId);
		return meetingRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<MeetingTask> getMeetingTaskById(Integer taskId) {
		log.info("inside the getMeetingTaskById method...{}", taskId);
		return meetingTaskRepository.findById(taskId);
	}

	@Override
	public List<MeetingTask> getTodaysMeetingTask(LocalDate todayAsDate, String time) {
		log.info("inside the getTodaysMeetingTask method...{} {}", todayAsDate, time);
		return meetingTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayAsDate, time, true);
	}

	@Override
	@Cacheable(value = MEETING_TASK)
	public List<MeetingTask> getAllMeetingTask() {
		log.info("inside the getAllMeetingTask method...");
		return meetingTaskRepository.findAll();
	}

	@Override
	public List<Meetings> getMeetingByLeadIdAndIsOpportunity(Integer leadId) {
		log.info("inside the getMeetingByLeadIdAndIsOpportunity method...{}", leadId);
		return meetingRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, false);
	}

	@Override
	@Cacheable(value = MEETINGS, key = "#isOpportunity", condition = "#isOpportunity!=null")
	public List<Meetings> getAllLeasMeetings(boolean isOpportunity) {
		log.info("inside the getAllLeasMeetings method...{}", isOpportunity);
		return meetingRepository.findByIsOpportunityOrderByCreatedDateDesc(isOpportunity);
	}
}
