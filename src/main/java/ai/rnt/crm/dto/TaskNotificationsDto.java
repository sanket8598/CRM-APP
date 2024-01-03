package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class TaskNotificationsDto {

	private Integer notifId;
	private CallTaskDto callTask;
	private VisitTaskDto visitTask;
	private MeetingTaskDto meetingTask;
	private LeadTaskDto leadTask;
	private LeadDto leads;
	private boolean notifStatus;
}
