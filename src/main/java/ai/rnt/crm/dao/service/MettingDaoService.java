package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.MettingDto;
import ai.rnt.crm.entity.Meetings;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
public interface MettingDaoService extends CrudService<Meetings, MettingDto> {

	Meetings addMetting(Meetings metting);

}
