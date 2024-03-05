package ai.rnt.crm.dao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.entity.ExcelHeaderMaster;
import ai.rnt.crm.repository.ExcelHeaderRepository;

/**
 * @author Nikhil Gaikwad
 * @since 05/03/2024.
 *
 */
class ExcelHeaderDaoServiceImplTest {

	@InjectMocks
	ExcelHeaderDaoServiceImpl excelHeaderDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	ExcelHeaderMaster excelHeaderMaster;

	@Mock
	private ExcelHeaderRepository excelHeaderRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(excelHeaderDaoServiceImpl).build();
	}

	@Test
	void getExcelHeadersFromDBTest() {
		List<ExcelHeaderMaster> excelHeaders = new ArrayList<>();
		excelHeaders.add(new ExcelHeaderMaster());
		excelHeaders.add(new ExcelHeaderMaster());
		when(excelHeaderRepository.findAll()).thenReturn(excelHeaders);
		List<ExcelHeaderMaster> retrievedExcelHeaders = excelHeaderDaoServiceImpl.getExcelHeadersFromDB();
		assertEquals(excelHeaders.size(), retrievedExcelHeaders.size());
	}
}
