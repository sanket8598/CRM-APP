package ai.rnt.crm.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.exception.CRMException;

class EmailUtilTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	EmailUtil emailUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(emailUtil).build();
	}

	@Test
	void testFormatDateException() {
		Throwable exception = assertThrows(CRMException.class, () -> {
			EmailUtil.formatDate(null);
		});
		String expectedErrorMessage = "Got Exception while converting task due date..null";
		assertFalse(exception.getMessage().contains(expectedErrorMessage));
	}
}
