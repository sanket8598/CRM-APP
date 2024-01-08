package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.LeadTaskDtoMapper.TO_GET_NOTIFICATION_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static org.springframework.http.HttpStatus.FOUND;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.TaskNotificationDaoService;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.TaskNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskNotificationServiceImpl implements TaskNotificationService {

	private final TaskNotificationDaoService taskNotificationDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getNotification(Integer staffId) {
		log.info("inside the getNotification method...{}", staffId);
		EnumMap<ApiResponse, Object> notification = new EnumMap<>(ApiResponse.class);
		try {
			Map<String, Object> countData = new HashMap<>();
			List<TaskNotifications> notifyData = taskNotificationDaoService.getNotifications(staffId);
			countData.put("Count", notifyData.stream().count());
			countData.put("Notifications", TO_GET_NOTIFICATION_DTOS.apply(notifyData));
			notification.put(DATA, countData);
			notification.put(SUCCESS, true);
			return new ResponseEntity<>(notification, FOUND);
		} catch (Exception e) {
			log.info("Got exception while getting notification..{}" + e.getMessage());
			throw new CRMException(e);
		}
	}
}
