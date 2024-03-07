package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DashboardDataDtoTest {

	@Test
	void testEqualsAndHashCode() {
		DashboardDataDto dto1 = createSampleDto();
		DashboardDataDto dto2 = createSampleDto();

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());

		dto2.setInPipelineAmount("Different amount");
		assertNotEquals(dto1, dto2);
	}

	@Test
	void testToString() {
		DashboardDataDto dto = createSampleDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testCanEqual() {
		DashboardDataDto dto = createSampleDto();
		assertTrue(dto.canEqual(new DashboardDataDto()));
	}

	private DashboardDataDto createSampleDto() {
		DashboardDataDto dto = new DashboardDataDto();
		dto.setInPipelineAmount("Amount");
		dto.setInPipelineOpprt(10);
		dto.setWinOpprt(5);
		dto.setLostOpprt(3);
		dto.setGrapdata(new ArrayList<>());
		return dto;
	}

}
