package ai.rnt.crm.dao.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.PhoneCallTask;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
public interface CallDaoService extends CrudService<Call, CallDto> {

	Call call(Call call);

	List<Call> getCallsByLeadId(Integer leadId);

	Optional<Call> getCallById(Integer callId);

	PhoneCallTask addCallTask(PhoneCallTask phoneCallTask);

	Optional<PhoneCallTask> getCallTaskById(Integer taskId);

	List<PhoneCallTask> getAllTask();

	List<PhoneCallTask> getTodaysCallTask(LocalDate todayAsDate, String time);

	List<Call> getCallsByLeadIdAndIsOpportunity(Integer leadId);

	List<Call> getAllLeadCalls(boolean isOpportunity);
}
