package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 28/11/2023
 * @version 1.0
 *
 */
@Getter
@Setter
public class CallTaskDto extends TaskDto{
	
	private static final long serialVersionUID = 6696961898687777620L;

	private Integer callTaskId;

	private EmployeeDto assignTo;

}
