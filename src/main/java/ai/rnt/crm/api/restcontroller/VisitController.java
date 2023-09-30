package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.ADD;
import static ai.rnt.crm.constants.ApiConstants.VISIT;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.VisitService;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 14-09-2023.
 *
 */
@RestController
@RequestMapping(VISIT)
@CrossOrigin("*")
@RequiredArgsConstructor
public class VisitController {

	private final VisitService visitService;

	@PostMapping(ADD)
	public ResponseEntity<EnumMap<ApiResponse, Object>> saveVisit(@RequestBody VisitDto dto) {
		return visitService.saveVisit(dto);
	}

	@DeleteMapping("/deleteVisit/{visitId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteVisit(@PathVariable Integer visitId) {
		return visitService.deleteVisit(visitId);
	}

	@PutMapping("/updateVisit/{visitId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> visitMarkAsCompleted(@PathVariable Integer visitId) {
		return visitService.visitMarkAsCompleted(visitId);
	}
}
