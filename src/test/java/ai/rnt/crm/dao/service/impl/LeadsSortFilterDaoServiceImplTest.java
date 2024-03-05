package ai.rnt.crm.dao.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.entity.LeadSortFilter;
import ai.rnt.crm.repository.LeadSortFilterRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 * 
 */
class LeadsSortFilterDaoServiceImplTest {

	@InjectMocks
	LeadsSortFilterDaoServiceImpl leadsSortFilterDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	LeadSortFilter leadSortFilter;

	@Mock
	private LeadSortFilterRepository LeadSortFilterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(leadsSortFilterDaoServiceImpl).build();
	}

	@Test
	void saveTest() {
		LeadSortFilter leadSortFilter = new LeadSortFilter(); // Create a LeadSortFilter instance if needed
		when(LeadSortFilterRepository.save(any(LeadSortFilter.class))).thenReturn(leadSortFilter);
		Optional<LeadSortFilterDto> leadSortFilterDtoOptional = leadsSortFilterDaoServiceImpl.save(leadSortFilter);
		verify(LeadSortFilterRepository).save(leadSortFilter);
		assertTrue(leadSortFilterDtoOptional.isPresent());
	}

	@Test
	void findSortFilterByEmployeeStaffIdTest() {
		Integer loggedInStaffId = 1477;
		LeadSortFilter leadSortFilter = new LeadSortFilter();
		when(LeadSortFilterRepository.findTopByEmployeeStaffIdOrderByLeadSortFilterIdDesc(loggedInStaffId))
				.thenReturn(Optional.of(leadSortFilter));
		Optional<LeadSortFilter> result = leadsSortFilterDaoServiceImpl
				.findSortFilterByEmployeeStaffId(loggedInStaffId);
		verify(LeadSortFilterRepository).findTopByEmployeeStaffIdOrderByLeadSortFilterIdDesc(loggedInStaffId);
		assertTrue(result.isPresent());
	}
}
