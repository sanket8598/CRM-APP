package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class TaskNotificationsDto {

	private Integer notifId;
	private GetCallTaskDto callTask;
	private GetVisitTaskDto visitTask;
	private GetMeetingTaskDto meetingTask;
	private GetLeadTaskDto leadTask;
	private EditLeadDto leads;
	private boolean notifStatus;
}
