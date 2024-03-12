package ai.rnt.crm.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Leads;

class LeadsCardUtilTest {

	@Test
	void testShortName() {
		String fName = "John";
		String lName = "Doe";
		assertEquals("JD", LeadsCardUtil.shortName(fName, lName));
	}

	@Test
	void testShortNameNullFirstName() {
		String fName = null;
		String lName = "Doe";
		assertNull(LeadsCardUtil.shortName(fName, lName));
	}

	@Test
	void testShortNameNullLastName() {
		String fName = "John";
		String lName = null;
		assertNull(LeadsCardUtil.shortName(fName, lName));
	}

	@Test
	void testShortNameBothNull() {
		String fName = null;
		String lName = null;
		assertNull(LeadsCardUtil.shortName(fName, lName));
	}

	@Test
	void testShortNameWithFullName() {
		String fullName = "John Doe";
		String result = LeadsCardUtil.shortName(fullName);
		assertEquals("JD", result);
	}

	@Test
	void testShortNameWithSingleName() {
		String fullName = "John";
		String result = LeadsCardUtil.shortName(fullName);
		assertEquals("J", result);
	}

	@Test
	void testShortNameWithException() {
		String fullName = "John Doe";
		LeadsCardUtil.shortName(fullName);
	}
}
