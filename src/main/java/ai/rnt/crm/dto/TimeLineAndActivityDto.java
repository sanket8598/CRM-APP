package ai.rnt.crm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeLineAndActivityDto {

	private Integer id;
	private String type;
	private String subject;
	private String body;
	private String shortName;
	private String createdOn;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private String dueDate;

}
