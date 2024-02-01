package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class TaskNotificationsDto {

	private Integer notifId;
	private boolean notifStatus;
	private String message;
}
