package ai.rnt.crm.dao.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
public interface MeetingDaoService extends CrudService<Meetings, MeetingDto> {

	Meetings addMeeting(Meetings metting);

	Optional<Meetings> getMeetingById(Integer meetingId);

	MeetingTask addMeetingTask(MeetingTask meetingTask);

	List<Meetings> getMeetingByLeadId(Integer leadId);

	Optional<MeetingTask> getMeetingTaskById(Integer taskId);

	List<MeetingTask> getTodaysMeetingTask(LocalDate todayAsDate, String time);

	List<MeetingTask> getAllMeetingTask();

	List<Meetings> getMeetingByLeadIdAndIsOpportunity(Integer leadId);

	List<Meetings> getAllLeasMeetings(boolean b);

}
