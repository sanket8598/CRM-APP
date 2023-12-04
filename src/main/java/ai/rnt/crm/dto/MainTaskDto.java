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
	

	public MainTaskDto(Integer id, String subject, String status, String type,Date dueDate) {
		super();
		this.id = id;
		this.subject = subject;
		this.status = status;
		this.type = type;
		this.dueDate = dueDate;
	}

	public MainTaskDto() {
	}
	
	
}
