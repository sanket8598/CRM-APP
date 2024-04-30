package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.RoleConstants.CHECK_BOTH_ACCESS;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.TaskNotificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notification/")
@RequiredArgsConstructor
public class TaskNotificationsController {

	private final TaskNotificationService taskNotificationService;

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@GetMapping("/get/{staffId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getNotification(@PathVariable Integer staffId) {
		return taskNotificationService.getNotification(staffId);
	}

	@PreAuthorize(CHECK_BOTH_ACCESS)
	@PutMapping("/update/{notifId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> seenNotification(@PathVariable Integer notifId) {
		return taskNotificationService.seenNotification(notifId);
	}
}
