package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TimeLineActivityDtoTest {

	@Test
	void testSetAndGetTimeLineActivityData() {
		TimeLineActivityDto timeLineActivityDto = new TimeLineActivityDto() {
		};
		TimeLineActivityDto dto = new TimeLineActivityDto() {
		};
		TimeLineActivityDto dto1 = new TimeLineActivityDto() {
		};
		timeLineActivityDto.setCreatedOn("2024-03-04");
		timeLineActivityDto.setWaitTwoDays(true);
		timeLineActivityDto.setOverDue(false);
		dto1.setCreatedOn("2024-03-04");
		dto1.setWaitTwoDays(true);
		dto1.setOverDue(false);
		String createdOn = timeLineActivityDto.getCreatedOn();
		boolean waitTwoDays = timeLineActivityDto.isWaitTwoDays();
		Boolean overDue = timeLineActivityDto.getOverDue();
		timeLineActivityDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertNotNull(createdOn);
		assertEquals("2024-03-04", createdOn);
		assertEquals(true, waitTwoDays);
		assertEquals(false, overDue);
	}
}
