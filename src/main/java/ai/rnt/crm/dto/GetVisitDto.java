package ai.rnt.crm.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetVisitDto {

	private Integer visitId;

	private String location;

	private String subject;

	private String duration;

	private String content;

	private String comment;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date startDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date endDate;

	private String participates;

	private String startTime;

	private String endTime;

	private boolean allDay;

	private List<GetVisitTaskDto> visitTasks;
}
