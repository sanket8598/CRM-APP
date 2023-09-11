package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class LeadsCardDto {

	private Integer leadId;

	private String firstName;

	private String lastName;

	private String topic;
	
	private String shortName;

}
