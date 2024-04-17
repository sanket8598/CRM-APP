package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.ProposalDaoService;
import ai.rnt.crm.dto.opportunity.ProposalDto;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.Proposal;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.util.StringUtil;

@ExtendWith(MockitoExtension.class)
class ProposalServiceImplTest {

	@InjectMocks
	private ProposalServiceImpl proposalServiceImpl;

	@Mock
	private StringUtil stringUtil;

	@Mock
	private ProposalDto dto;

	@Mock
	private Proposal proposal;

	@Mock
	private Opportunity opportunity;

	@Mock
	private ProposalDaoService proposalDaoService;

	@Mock
	private OpportunityDaoService opportunityDaoService;

	@Test
	void testGenerateProposalId_Success() {
		mock(StringUtil.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalServiceImpl.generateProposalId();
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.DATA));
		assertTrue(responseEntity.getBody().containsKey(ApiResponse.SUCCESS));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void addProposalTestSuccess() {
		dto.setPropId(1);
		Integer optyId = 1;
		proposal.setOpportunity(opportunity);
		Proposal mockedCall = mock(Proposal.class);
		when(opportunityDaoService.findOpportunity(optyId)).thenReturn(Optional.of(opportunity));
		when(proposalDaoService.saveProposal(any(Proposal.class))).thenReturn(mockedCall);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalServiceImpl.addProposal(dto, optyId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Proposal Added Successfully", responseEntity.getBody().get(ApiResponse.MESSAGE));
		assertTrue((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void addProposalTestElseBlock() {
		dto.setPropId(1);
		Integer optyId = 1;
		proposal.setOpportunity(opportunity);
		when(opportunityDaoService.findOpportunity(optyId)).thenReturn(Optional.of(opportunity));
		when(proposalDaoService.saveProposal(any(Proposal.class))).thenReturn(null);
		ResponseEntity<EnumMap<ApiResponse, Object>> responseEntity = proposalServiceImpl.addProposal(dto, optyId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Proposal Not Added", responseEntity.getBody().get(ApiResponse.MESSAGE));
		assertFalse((boolean) responseEntity.getBody().get(ApiResponse.SUCCESS));
	}

	@Test
	void addProposalTestWithException() {
		ProposalDto callDto = new ProposalDto();
		Integer optyId = 1;
		when(opportunityDaoService.findOpportunity(anyInt()))
				.thenThrow(new ResourceNotFoundException("Opportunity", "optyId", optyId));
		assertThrows(CRMException.class, () -> proposalServiceImpl.addProposal(callDto, optyId));
		verify(opportunityDaoService, times(1)).findOpportunity(anyInt());
	}
}
