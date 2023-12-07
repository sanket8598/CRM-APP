package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.entity.LeadTask;

public interface LeadTaskDaoService extends CrudService<LeadTask, LeadTaskDto> {

	LeadTask addTask(LeadTask leadTask);

}
