package ai.rnt.crm.dao.service;

import java.util.Optional;

import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.entity.LeadSortFilter;

public interface LeadSortFilterDaoService extends CrudService<LeadSortFilter, LeadSortFilterDto> {

	Optional<LeadSortFilter> findSortFilterByEmployeeStaffId(Integer loggedInStaffId);

}
