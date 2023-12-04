package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.EXCEL;
import static ai.rnt.crm.constants.ApiConstants.DOWNLOAD;
import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(EXCEL)
@Slf4j
public class ExcelFileController {

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping(DOWNLOAD)
	public ResponseEntity<Resource> downloadFile() throws IOException {
		String fileName = "excelFormat.xlsx";
		Resource resource = new ClassPathResource(fileName);
		if (!resource.exists()) {
			log.error("File not exist in the directory. i.e src/main/resource.. {}", fileName);
			throw new FileNotFoundException("File not found: " + fileName);
		}
		long contentLength = resource.contentLength();
		String contentType;
		if (fileName.endsWith(".pdf"))
			contentType = MediaType.APPLICATION_PDF_VALUE;
		else
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.header(HttpHeaders.CONTENT_LENGTH, Long.toString(contentLength))
				.contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
}
