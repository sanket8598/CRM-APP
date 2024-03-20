package ai.rnt.crm.dto;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetVisitTaskDto extends GetTaskDto{

	private static final long serialVersionUID = 2486818589279180726L;
	
	private Integer visitTaskId;

	public String getVisitTaskDueDate() {
		return convertDateDateWithTime(getDueDate(), getDueTime12Hours());
	}
}
