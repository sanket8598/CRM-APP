package ai.rnt.crm.dao.service;

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

}