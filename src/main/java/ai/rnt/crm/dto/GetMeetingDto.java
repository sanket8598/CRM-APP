package ai.rnt.crm.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetMeetingDto {

	private Integer meetingId;

	private String meetingTitle;

	private String participates;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date startDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date endDate;

	private String startTime;

	private String endTime;

	private boolean allDay;

	private String location;

	private String description;

	private String meetingMode;

	private List<MeetingAttachmentsDto> meetingAttachments;

	private List<GetMeetingTaskDto> meetingTasks;

}
