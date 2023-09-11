package ai.rnt.crm.dao.service;

import java.util.List;

import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.entity.CountryMaster;

public interface CountryDaoService extends CrudService<CountryMaster, CountryDto> {

	List<CountryMaster> getAllCountry();

}
