package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.entity.AddCall;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
public interface AddCallDaoService extends CrudService<AddCall, AddCallDto> {

	Object addCall(AddCall addCall);

}
