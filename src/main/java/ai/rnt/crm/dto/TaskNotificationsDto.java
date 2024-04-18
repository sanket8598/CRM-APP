package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskNotificationsDto {

	private Integer notifId;
	private boolean notifStatus;
	private String message;
	private Integer callTaskId;
	private Integer meetingTaskId;
	private Integer visitTaskId;
	private Integer leadTaskId;
	private Integer leadId;
	private Integer optyId;
}
