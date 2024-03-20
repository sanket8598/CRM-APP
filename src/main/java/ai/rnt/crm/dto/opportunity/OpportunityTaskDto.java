package ai.rnt.crm.dto.opportunity;

import ai.rnt.crm.dto.EntityTaskDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 19-02-2024
 * @version 1.2
 *
 */
@Getter
@Setter
public class OpportunityTaskDto extends EntityTaskDto{

	private static final long serialVersionUID = 8492122295506917268L;
	
	private Integer optyTaskId;

}
