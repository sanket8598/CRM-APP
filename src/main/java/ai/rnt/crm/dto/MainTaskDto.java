package ai.rnt.crm.dto;

import lombok.Data;

@Data
public  class MainTaskDto {
	private Integer id;

	private String subject;
	
	private String status;

	private String type;

	public MainTaskDto(Integer id, String subject, String status, String type) {
		super();
		this.id = id;
		this.subject = subject;
		this.status = status;
		this.type = type;
	}

	public MainTaskDto() {
	}
	
	
}
