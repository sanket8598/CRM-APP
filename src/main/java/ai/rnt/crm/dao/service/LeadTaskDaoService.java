package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.entity.LeadTask;

public interface LeadTaskDaoService extends CrudService<LeadTask, LeadTaskDto> {

	LeadTask addTask(LeadTask leadTask);

	Optional<LeadTask> getTaskById(Integer taskId);

	List<LeadTask> getAllTask();

}
