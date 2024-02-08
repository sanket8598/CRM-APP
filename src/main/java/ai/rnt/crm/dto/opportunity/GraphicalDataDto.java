package ai.rnt.crm.dto.opportunity;

import lombok.Data;

@Data
public class GraphicalDataDto {

	private String topic;
	
	private String status;
	
	private String companyName;
	
	private String phase;
	
	private String budgetAmount;
	
	private Double bubbleSize;
	
	private String closedDate;
}
