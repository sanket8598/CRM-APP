package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.DashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard/")
@RequiredArgsConstructor
@Validated
public class DashboardController {

	private final DashboardService dashboardService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/data")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getDashboardData() {
		return dashboardService.getDashboardData();
	}
}
