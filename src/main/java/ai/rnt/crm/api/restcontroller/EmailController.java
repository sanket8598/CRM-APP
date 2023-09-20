package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.EMAIL;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EMAIL)
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;

	/**
	 * This method is used to save and send the email.
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 12/09/2023.
	 * @return mail
	 */
	@PostMapping("/addEmail/{leadId}/{status}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(@RequestBody EmailDto dto,
			@PathVariable(name = "leadId") Integer leadId,@PathVariable(name="status",required = false)String status) {
		return emailService.addEmail(dto, leadId,status);
	}
	
	@GetMapping("/getMailId/{addMailId}/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> checkMailId(@PathVariable Integer addMailId,
			@PathVariable Integer leadId) {
		return emailService.checkMailId(addMailId, leadId);
	}

}
