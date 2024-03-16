package ai.rnt.crm.api.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ExcelFileControllerTest {

    @InjectMocks
    ExcelFileController excelFileController;
    
    @BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		MockMvcBuilders.standaloneSetup(excelFileController).build();
	}
    
    @Test
     void testDownloadFile_Success() throws Exception {
        String fileName = "excelFormat.xlsx";
        Resource resource = Mockito.mock(Resource.class);
        when(resource.exists()).thenReturn(true);
        when(resource.contentLength()).thenReturn(1024L); 
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
        expectedHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        expectedHeaders.setContentLength(1024L);
        ResponseEntity<Resource> downloadFile = excelFileController.downloadFile();
        assertEquals(HttpStatus.OK, downloadFile.getStatusCode());
    }
}
