package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TimeLineActivityDto {

	private String createdOn;

	private boolean waitTwoDays;

	private Boolean overDue;
}
