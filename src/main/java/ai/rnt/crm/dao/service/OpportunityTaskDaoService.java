package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.entity.OpportunityTask;

public interface OpportunityTaskDaoService extends CrudService<OpportunityTask, OpportunityTaskDto> {

	List<OpportunityTask> getAllTask();

	OpportunityTask addOptyTask(OpportunityTask opportunityTask);

	Optional<OpportunityTask> getOptyTaskById(Integer taskId);

}
