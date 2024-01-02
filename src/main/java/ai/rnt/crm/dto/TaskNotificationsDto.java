package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class TaskNotificationsDto {

	private Integer notifId;
	private CallTaskDto callTask;
	private VisitTaskDto visitTask;
	private MeetingTaskDto meetingTask;
	private boolean notifStatus;
}
