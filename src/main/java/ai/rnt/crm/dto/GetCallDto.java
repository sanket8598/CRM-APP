package ai.rnt.crm.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCallDto {

	private Integer callId;
	private String subject;
	private EmployeeDto callFrom;
	private String callTo;
	private String direction;
	private String phoneNo;
	private String comment;
	private String duration;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date startDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date endDate;

	private String startTime;

	private String endTime;

	private String startTime12Hours;

	private String endTime12Hours;

	private boolean allDay;

	private List<GetCallTaskDto> callTasks;

}
