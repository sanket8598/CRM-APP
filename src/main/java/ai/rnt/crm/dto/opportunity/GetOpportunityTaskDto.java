package ai.rnt.crm.dto.opportunity;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;

import ai.rnt.crm.dto.GetEntityTaskDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOpportunityTaskDto extends GetEntityTaskDto{

	private static final long serialVersionUID = -6133870831724484138L;
	
	private Integer optyTaskId;

	public String getOpportunityTaskDueDate() {
		return convertDateDateWithTime(getDueDate(), getDueTime12Hours());
	}
}
