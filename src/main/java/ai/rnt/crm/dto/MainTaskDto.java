package ai.rnt.crm.dto;

import lombok.Data;

@Data
public  class MainTaskDto {
	private Integer id;

	private String subject;
	
	private String status;

	private String type;
	
	private String dueDate;
	
	private EmployeeDto assignTo;
	
	private Integer parentId;
	
	private boolean remainderOn;
	

	public MainTaskDto(Integer id, String subject, String status, String type,String dueDate,EmployeeDto assignTo,Integer parentId,Boolean remainderOn) {
		super();
		this.id = id;
		this.subject = subject;
		this.status = status;
		this.type = type;
		this.dueDate = dueDate;
		this.assignTo = assignTo;
		this.parentId = parentId;
		this.remainderOn = remainderOn;
	}

	public MainTaskDto() {
	}
	
	
}
