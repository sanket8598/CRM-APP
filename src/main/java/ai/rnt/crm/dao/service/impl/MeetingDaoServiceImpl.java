package ai.rnt.crm.dao.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.repository.MeetingRepository;
import ai.rnt.crm.repository.MeetingTaskRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MeetingDaoServiceImpl implements MeetingDaoService {

	private final MeetingRepository meetingRepository;
	private final MeetingTaskRepository meetingTaskRepository;

	@Override
	public Meetings addMeeting(Meetings metting) {
		return meetingRepository.save(metting);
	}

	@Override
	public Optional<Meetings> getMeetingById(Integer meetingId) {
		return meetingRepository.findById(meetingId);
	}

	@Override
	public MeetingTask addMeetingTask(MeetingTask meetingTask) {
		return meetingTaskRepository.save(meetingTask);
	}

	@Override
	public List<Meetings> getMeetingByLeadId(Integer leadId) {
		return meetingRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<MeetingTask> getMeetingTaskById(Integer taskId) {
		return meetingTaskRepository.findById(taskId);
	}

	@Override
	public List<MeetingTask> getTodaysMeetingTask(LocalDate todayAsDate, String time) {
		return meetingTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayAsDate, time, true);
	}

	@Override
	public List<MeetingTask> getAllMeetingTask() {
		return meetingTaskRepository.findAll();
	}
}
