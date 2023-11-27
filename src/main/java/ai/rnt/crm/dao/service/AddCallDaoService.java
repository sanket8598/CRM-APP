package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.entity.Call;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
public interface AddCallDaoService extends CrudService<Call, AddCallDto> {

	Call call(Call call);

	List<Call> getCallsByLeadId(Integer leadId);

	Optional<Call> getCallById(Integer callId);
}
