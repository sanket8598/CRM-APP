package ai.rnt.crm.api.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ExcelFileControllerTest {

	@Mock
	private Resource resource;

	private MockMvc mockMvc;

	@InjectMocks
	private ExcelFileController excelFileController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	//@Test
	void testDownloadFile() throws Exception {
		String fileName = "excelFormat.xlsx";
		String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		long contentLength = 100L;
		when(resource.exists()).thenReturn(true);
		when(resource.contentLength()).thenReturn(contentLength);
		when(resource.getFilename()).thenReturn(fileName);
		mockMvc = MockMvcBuilders.standaloneSetup(excelFileController).build();
		MockHttpServletResponse response = mockMvc
				.perform(get("/download").contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(status().isOk())
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"" + fileName + "\""))
				.andExpect(header().string("Content-Length", Long.toString(contentLength)))
				.andExpect(content().contentType(contentType)).andReturn().getResponse();
	}
}
