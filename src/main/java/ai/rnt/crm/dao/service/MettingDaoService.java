package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.MettingDto;
import ai.rnt.crm.entity.Mettings;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
public interface MettingDaoService extends CrudService<Mettings, MettingDto> {

	Mettings addMetting(Mettings metting);

}
