package ai.rnt.crm.dto;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCallTaskDto extends GetTaskDto{
	
	private static final long serialVersionUID = 89098244403103795L;
	
	private Integer callTaskId;

	public String getCallTaskDueDate() {
		return convertDateDateWithTime(getDueDate(), getDueTime12Hours());
	}
}
