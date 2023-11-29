package ai.rnt.crm.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @version - 1.0
 * @since 14-09-2023.
 *
 */
@Data
public class VisitDto {

	private Integer visitId;

	private String location;

	private String subject;

	private String content;

	private String comment;

	private String duration;

	@NotNull(message = "Start Date should not be null!!")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date startDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date endDate;
	
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> participates;

	private String startTime;

	private String endTime;

	private boolean allDay;

	private Integer leadId;

	private Integer visitBy;

}
