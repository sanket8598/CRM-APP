package ai.rnt.crm.dao.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadSortFilterDaoService;
import static ai.rnt.crm.dto_mapper.LeadSortFilterDtoMapper.TO_LEAD_SORT_FILTER_DTO;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.entity.LeadSortFilter;
import ai.rnt.crm.repository.LeadSortFilterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadsSortFilterDaoServiceImpl implements LeadSortFilterDaoService {

	private final LeadSortFilterRepository leadSortRepository;
	@Override
	public Optional<LeadSortFilterDto> save(LeadSortFilter leadSourceFilter) {
		return TO_LEAD_SORT_FILTER_DTO.apply(leadSortRepository.save(leadSourceFilter));
	}
	
	@Override
	public Optional<LeadSortFilter> findSortFilterByEmployeeStaffId(Integer loggedInStaffId) {
		return leadSortRepository.findTopByEmployeeStaffIdOrderByLeadSortFilterIdDesc(loggedInStaffId);
	}

}
