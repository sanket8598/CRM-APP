package ai.rnt.crm.dao;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.entity.CityMaster;

public interface CityDaoService extends CrudService<CityMaster, CityDto> {

	List<CityMaster> getAllCity();
	
	Optional<CityMaster> existCityByName(String cityName);

	CityMaster addCity(CityMaster city);

}
