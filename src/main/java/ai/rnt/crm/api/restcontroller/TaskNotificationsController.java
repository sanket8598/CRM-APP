package ai.rnt.crm.api.restcontroller;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.TaskNotificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notification/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TaskNotificationsController {

	private final TaskNotificationService taskNotificationService;

	@GetMapping("/get/{staffId}")
	public ResponseEntity<EnumMap<ApiResponse, Object>> getNotification(@PathVariable Integer staffId) {
		return taskNotificationService.getNotification(staffId);
	}
}
