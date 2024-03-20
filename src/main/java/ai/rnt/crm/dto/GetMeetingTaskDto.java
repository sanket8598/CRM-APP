package ai.rnt.crm.dto;

import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMeetingTaskDto extends GetTaskDto {

	private static final long serialVersionUID = -734548839937188061L;

	private Integer meetingTaskId;

	public String getMeetingTaskDueDate() {
		return convertDateDateWithTime(getDueDate(), getDueTime12Hours());
	}
}
