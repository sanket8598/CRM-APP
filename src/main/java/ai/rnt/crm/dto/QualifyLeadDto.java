package ai.rnt.crm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class QualifyLeadDto {

	private Integer leadId;

	private String customerNeed;

	private String proposedSolution;

	private ServiceFallsDto serviceFallsMaster;
	
	private Boolean isFollowUpRemainder;
	
	private String remainderVia;

	private String remainderDueAt;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date remainderDueOn;
}
