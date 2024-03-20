package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadTaskDto extends EntityTaskDto{

	private static final long serialVersionUID = -1019242512062959814L;
	
	private Integer leadTaskId;

}
