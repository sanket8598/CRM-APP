package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CALL;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.AddCallService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(CALL)
@CrossOrigin("*")
@RequiredArgsConstructor
public class AddCallController {

	private final AddCallService addCallService;

	/**
	 * @author Nikhil Gaikwad
	 * @version 1.0
	 * @since 11/09/2023.
	 * @return city
	 */
	@PostMapping("/add/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCall(@RequestBody AddCallDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return addCallService.addCall(dto, leadsId);
	}
}
