package ai.rnt.crm.dao.service;

import java.util.List;

import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.StateMaster;

public interface StateDaoService extends CrudService<StateMaster, StateDto> {

	List<StateMaster> getAllState();

}
