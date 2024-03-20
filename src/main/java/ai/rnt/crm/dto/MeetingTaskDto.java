package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 27/11/2023.
 *
 */
@Getter
@Setter
public class MeetingTaskDto extends TaskDto{

	private static final long serialVersionUID = 5544960105623841822L;
	private Integer meetingTaskId;
}
