package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DashboardDataDtoTest {

	@Test
	void testDashboardDataDto() {
		DashboardDataDto dashboardDataDto = new DashboardDataDto();
		GraphicalDataDto graphicalData1 = new GraphicalDataDto();
		graphicalData1.setTopic("test1");
		GraphicalDataDto graphicalData2 = new GraphicalDataDto();
		graphicalData2.setTopic("test");
		List<GraphicalDataDto> graphData = Arrays.asList(graphicalData1, graphicalData2);
		dashboardDataDto.setInPipelineAmount("10000");
		dashboardDataDto.setInPipelineOpprt(10);
		dashboardDataDto.setWinOpprt(5);
		dashboardDataDto.setLostOpprt(2);
		dashboardDataDto.setGrapdata(graphData);
		assertEquals("10000", dashboardDataDto.getInPipelineAmount());
		assertEquals(10, dashboardDataDto.getInPipelineOpprt());
		assertEquals(5, dashboardDataDto.getWinOpprt());
		assertEquals(2, dashboardDataDto.getLostOpprt());
		assertEquals(graphData, dashboardDataDto.getGrapdata());
	}
}
