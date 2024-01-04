package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 */
@Data
public class MeetingDto {

	private Integer meetingId;

	private String meetingTitle;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> participates;

	@Temporal(DATE)
	private Date startDate;

	@Temporal(DATE)
	private Date endDate;

	private String startTime;
	
	private String duration;

	private String endTime;

	private boolean allDay;

	private String location;

	private String description;

	private String meetingMode;

	private List<MeetingAttachmentsDto> meetingAttachments;

}
