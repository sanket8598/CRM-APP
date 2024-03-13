package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 */
@Getter
@Setter
public class MeetingDto {

	private Integer meetingId;

	@NotBlank(message = "Title should not be null or empty!!")
	private String meetingTitle;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@NotNull(message = "Attendees should not be null!!")
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

	private Boolean isOpportunity = false;

	private List<MeetingAttachmentsDto> meetingAttachments;

}
