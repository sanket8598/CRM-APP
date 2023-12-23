package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class TaskNotificationsDto {

	private Integer notifId;
	private CallTaskDto callTask;
	private VisitTaskDto visitTask;
	private MeetingTaskDto meetingTask;
	private String notifMessage;
	private boolean notifStatus;
}
