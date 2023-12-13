package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import ai.rnt.crm.entity.EmployeeMaster;
import lombok.Data;

/**
 * @author Nikhil Gaikwad
 * @since 28/11/2023
 * @version 1.0
 *
 */
@Data
public class VisitTaskDto {

	private Integer vistitTaskId;

	private String subject;

	private String status;

	private String priority;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	private String dueTime;

	private String description;

	private boolean remainderOn;

	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date remainderDueOn;

	private EmployeeMaster assinTo;
}
