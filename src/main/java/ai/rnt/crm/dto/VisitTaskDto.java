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
public class VisitTaskDto extends TaskDto{

	private static final long serialVersionUID = -4723575440666201452L;

	private Integer visitTaskId;

	private EmployeeDto assinTo;
}
