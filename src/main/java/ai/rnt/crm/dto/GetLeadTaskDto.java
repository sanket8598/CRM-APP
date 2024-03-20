package ai.rnt.crm.dto;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLeadTaskDto extends GetEntityTaskDto{

	private static final long serialVersionUID = -1019801939280716626L;

	private Integer leadTaskId;

	public String getLeadTaskDueDate() {
		return convertDateDateWithTime(getDueDate(), getDueTime12Hours());
	}
}
