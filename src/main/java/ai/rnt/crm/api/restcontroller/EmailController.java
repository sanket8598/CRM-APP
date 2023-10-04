package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.EMAIL;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	 * 
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 12/09/2023.
	 * @return mail
	 */
	@PostMapping("/addEmail/{leadId}/{status}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addEmail(@RequestBody EmailDto dto,
			@PathVariable(name = "leadId") Integer leadId,
			@PathVariable(name = "status", required = false) String status) {
		return emailService.addEmail(dto, leadId, status);
	}

	@GetMapping("/getMailId/{addMailId}/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> checkMailId(@PathVariable Integer addMailId,
			@PathVariable Integer leadId) {
		return emailService.checkMailId(addMailId, leadId);
	}

	@DeleteMapping("/deleteEmail/{mailId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteEmail(@PathVariable Integer mailId) {
		return emailService.deleteEmail(mailId);
	}

	@PutMapping("/assignEmail")
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignEmail(@RequestBody Map<String, Integer> map) {
		return emailService.assignEmail(map);
	}

	@GetMapping("/{mailId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getEmail(@PathVariable Integer mailId) {
		return emailService.getEmail(mailId);
	}

	@PutMapping("/editEmail/{leadId}/{status}/{mailId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> editEmail(@RequestBody EmailDto dto,
			@PathVariable(name = "leadId") Integer leadId,
			@PathVariable(name = "status", required = false) String status,
			@PathVariable(name = "mailId") Integer mailId) {
		return emailService.editEmail(dto, leadId, status,mailId);
	}
}
