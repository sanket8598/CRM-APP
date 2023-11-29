package ai.rnt.crm.dto;

import lombok.Data;

@Data
public abstract class TimeLineActivityDto {
	private String createdOn;
	private boolean waitTwoDays;
}
