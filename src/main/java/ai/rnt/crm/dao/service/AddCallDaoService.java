package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.entity.AddCall;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
public interface AddCallDaoService extends CrudService<AddCall, AddCallDto> {

	AddCall addCall(AddCall addCall);

	List<AddCall> getCallsByLeadId(Integer leadId);

	Optional<AddCall> getCallById(Integer callId);

}
