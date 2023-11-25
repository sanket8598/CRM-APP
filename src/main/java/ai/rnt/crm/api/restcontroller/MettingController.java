package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.METTING;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.MettingDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.MettingService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 * @return metting data
 */
@RestController
@RequestMapping(METTING)
@CrossOrigin("*")
@RequiredArgsConstructor
public class MettingController {

	private final MettingService mettingService;

	@PostMapping("/add/{leadId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> addMetting(@RequestBody @Valid MettingDto dto,
			@PathVariable(name = "leadId") Integer leadsId) {
		return mettingService.addMetting(dto, leadsId);
	}
}
