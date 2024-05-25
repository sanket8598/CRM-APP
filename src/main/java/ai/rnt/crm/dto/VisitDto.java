package ai.rnt.crm.dto;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.validation.FutureOrPresentTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version - 1.0
 * @since 14-09-2023.
 *
 */
@Getter
@Setter
@FutureOrPresentTime(
	    timefieldOne = "startTime", 
	      timefieldSec = "endTime",
	      dateFieldOne = "startDate",
	      dateFieldSec = "endDate",
	      message = "Date/time is not valid!!"
	    )
public class VisitDto {

	private Integer visitId;

	private String location;

	@NotBlank(message = "Subject should not be null or empty!!")
	private String subject;

	private String content;

	private String comment;

	private String duration;

	@NotNull(message = "Start Date should not be null!!")
	@Temporal(DATE)
	@FutureOrPresent(message = "Start date must not be smaller than today's date!!")
	private Date startDate;

	@Temporal(DATE)
	@NotNull(message = "End Date should not be null!!")
	@FutureOrPresent(message = "End date must not be smaller than today's date!!")
	private Date endDate;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@NotNull(message = "Attendees should not be null!!")
	private List<String> participates;

	private String startTime;

	private String endTime;

	private boolean allDay;

	private Integer leadId;

	private Integer visitBy;

	private Boolean isOpportunity = false;

}
