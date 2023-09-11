package ai.rnt.crm.dao.service;

import java.util.List;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.entity.CityMaster;

public interface CityDaoService extends CrudService<CityMaster, CityDto> {

	List<CityMaster> getAllCity();

}
