package ai.rnt.crm.dto.opportunity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDataDto {

	private String inPipelineAmount;

	private long inPipelineOpprt;

	private long winOpprt;

	private long lostOpprt;

	private List<GraphicalDataDto> grapdata;

}
