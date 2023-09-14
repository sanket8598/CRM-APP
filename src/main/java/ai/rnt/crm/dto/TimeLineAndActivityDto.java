package ai.rnt.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeLineAndActivityDto {
	
	private String type;
	private String subject;
	private String body;
	private String shortName;
	private String createdOn;

}
