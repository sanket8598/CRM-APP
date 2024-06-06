package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DescriptionDtoTest {

	@Test
	void testGettersAndSetters() {
		DescriptionDto dto = new DescriptionDto();
		dto.setDescId(1);
		dto.setSubject("Test Subject");
		dto.setDesc("Test Description");
		dto.setAction("Test Action");
		dto.setType("Test Type");
		dto.setDate(LocalDate.now().plusDays(1));
		dto.setGetDate(LocalDate.now());
		dto.setStatus("Test Status");
		dto.setIsOpportunity(true);
		assertEquals(1, dto.getDescId());
		assertEquals("Test Subject", dto.getSubject());
		assertEquals("Test Description", dto.getDesc());
		assertEquals("Test Action", dto.getAction());
		assertEquals("Test Type", dto.getType());
		assertEquals(LocalDate.now().plusDays(1), dto.getDate());
		assertEquals(LocalDate.now(), dto.getGetDate());
		assertEquals("Test Status", dto.getStatus());
		assertTrue(dto.getIsOpportunity());
	}
}
