package ai.rnt.crm.dao.service.impl;

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
}
