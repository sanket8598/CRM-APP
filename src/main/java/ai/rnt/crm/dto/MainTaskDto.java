package ai.rnt.crm.dto;

import java.util.Date;

import lombok.Data;

@Data
public  class MainTaskDto {
	private Integer id;

	private String subject;
	
	private String status;

	private String type;
	
	private Date dueDate;
	
	private EmployeeDto assignTo;
	
	private String parentId;
	

	public MainTaskDto(Integer id, String subject, String status, String type,Date dueDate,EmployeeDto assignTo) {
		super();
		this.id = id;
		this.subject = subject;
		this.status = status;
		this.type = type;
		this.dueDate = dueDate;
		this.assignTo = assignTo;
	}

	public MainTaskDto() {
	}
	
	
}