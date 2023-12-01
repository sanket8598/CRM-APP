package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.EXCEL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(EXCEL)
@Slf4j
public class ExcelFileController {
	
//	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadImage(HttpServletResponse response) throws IOException{
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		String fileName=""+File.separator+"static"+File.separator+"excelFormat.xlsx";
		ClassPathResource resource = new ClassPathResource(fileName);
		InputStream inputStream = resource.getInputStream();
			StreamUtils.copy(inputStream, response.getOutputStream());
	}
}
