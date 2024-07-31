package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraphicalDataDto {

	private Integer opportunityId;
	
	private String topic;

	private String status;

	private String companyName;

	private String phase;

	private String budgetAmount;

	private Double bubbleSize;

	private String closedDate;
}
