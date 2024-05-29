package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.entity.ProposalServices;
import ai.rnt.crm.repository.ProposalServicesRepository;

@ExtendWith(MockitoExtension.class)
class ProposalServicesDaoServiceImplTest {

	@InjectMocks
	ProposalServicesDaoServiceImpl proposalServicesDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@Mock
	ProposalServicesRepository proposalServicesRepository;

	@Mock
	ProposalServicesDto proposalServicesDto;

	@Mock
	ProposalServices proposalServices;

	@Test
	 void testSave() throws Exception {
	      when(proposalServicesRepository.save(proposalServices)).thenReturn(proposalServices);
	      Optional<ProposalServicesDto> savedDto = proposalServicesDaoServiceImpl.save(proposalServices);
	      assertTrue(savedDto.isPresent());
	      verify(proposalServicesRepository, times(1)).save(proposalServices);
	    }

	@Test
	void testFindById() {
		Integer propServiceId = 1;
		Optional<ProposalServices> optionalProposalServices = Optional.of(proposalServices);
		when(proposalServicesRepository.findById(propServiceId)).thenReturn(optionalProposalServices);
		Optional<ProposalServices> foundProposalServices = proposalServicesDaoServiceImpl.findById(propServiceId);
		assertTrue(foundProposalServices.isPresent());
		assertEquals(proposalServices, foundProposalServices.get());
		verify(proposalServicesRepository, times(1)).findById(propServiceId);
	}
}
