package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.GetMeetingDto;
import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.entity.Meetings;

class MeetingDtoMapperTest {

	@Test
	void testToMeeting() {
		MeetingDto meetingDto = new MeetingDto();
		Optional<Meetings> meetingsOptional = MeetingDtoMapper.TO_MEETING.apply(meetingDto);
		assertNotNull(meetingsOptional);
	}

	@Test
	void testToMeetings() {
		Collection<MeetingDto> meetingDtoCollection = new ArrayList<>();
		List<Meetings> meetingsList = MeetingDtoMapper.TO_MEETINGS.apply(meetingDtoCollection);
		assertNotNull(meetingsList);
	}

	@Test
	void testToMeetingDto() {
		Meetings meetings = new Meetings();
		Optional<MeetingDto> meetingDtoOptional = MeetingDtoMapper.TO_MEETING_DTO.apply(meetings);
		assertNotNull(meetingDtoOptional);
	}

	@Test
	void testToMeetingDtos() {
		Collection<Meetings> meetingsCollection = new ArrayList<>();
		List<MeetingDto> meetingDtoList = MeetingDtoMapper.TO_MEETING_DTOS.apply(meetingsCollection);
		assertNotNull(meetingDtoList);
	}

	@Test
	void testToGetMeetingDto() {
		Meetings meetings = new Meetings();
		Optional<GetMeetingDto> getMeetingDtoOptional = MeetingDtoMapper.TO_GET_MEETING_DTO.apply(meetings);
		assertNotNull(getMeetingDtoOptional);
	}
}
