package ai.rnt.crm.dto.opportunity;

import java.util.List;

import lombok.Data;

@Data
public class DashboardDataDto {
	
	private String inPipelineAmount;
	
	private long inPipelineOpprt;
	
	private long winOpprt;
	
	private long lostOpprt;
	
	private List<GraphicalDataDto> grapdata;

}
