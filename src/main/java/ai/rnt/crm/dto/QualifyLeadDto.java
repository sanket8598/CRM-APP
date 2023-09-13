package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class QualifyLeadDto {

	private Integer leadId;

	private String customerNeed;

	private String proposedSolution;

	private ServiceFallsDto serviceFallsMaster;
}
