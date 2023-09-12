package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.AddEmail;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 *
 */
public interface EmailDaoService extends CrudService<AddEmail, EmailDto> {

	AddEmail addEmail(AddEmail addEmail);

}