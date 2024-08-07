package ai.rnt.crm.dto_mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.dto.LeadDashboardDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.Leads;

class LeadsDtoMapperTest {

	@Test
	void testToLead() {
		LeadDto leadDto = new LeadDto();
		Optional<Leads> leadsOptional = LeadsDtoMapper.TO_LEAD.apply(leadDto);
		assertNotNull(leadsOptional);
		assertTrue(leadsOptional.isPresent());
	}

	@Test
	void testToLeads() {
		Collection<LeadDto> leadDtos = new ArrayList<>();
		List<Leads> leadsList = LeadsDtoMapper.TO_LEADS.apply(leadDtos);
		assertNotNull(leadsList);
		assertEquals(leadDtos.size(), leadsList.size());
	}

	@Test
	void testToLeadDto() {
		Leads leads = new Leads();
		Optional<LeadDto> leadDtoOptional = LeadsDtoMapper.TO_LEAD_DTO.apply(leads);
		assertNotNull(leadDtoOptional);
	}

	@Test
	void testToLeadDtos() {
		Collection<Leads> leadsCollection = new ArrayList<>();
		List<LeadDto> leadDtoList = LeadsDtoMapper.TO_LEAD_DTOS.apply(leadsCollection);
		assertNotNull(leadDtoList);
	}

	@Test
	void testToDashboardLeadDtos() {
		Collection<Leads> leadsCollection = new ArrayList<>();
		List<LeadDashboardDto> leadDashboardDtoList = LeadsDtoMapper.TO_DASHBOARD_LEADDTOS.apply(leadsCollection);
		assertNotNull(leadDashboardDtoList);
	}

	@Test
	void testToDashboardCardsLeadDto() {
		Leads leads = new Leads();
		Optional<LeadsCardDto> leadsCardDtoOptional = LeadsDtoMapper.TO_DASHBOARD_CARDS_LEADDTO.apply(leads);
		assertNotNull(leadsCardDtoOptional);
	}

	@Test
	void testToDashboardCardsLeadDtos() {
		Collection<Leads> leadsCollection = new ArrayList<>();
		List<LeadsCardDto> leadsCardDtoList = LeadsDtoMapper.TO_DASHBOARD_CARDS_LEADDTOS.apply(leadsCollection);
		assertNotNull(leadsCardDtoList);
	}

	@Test
	void testToEditLeadDto() {
		Leads leads = new Leads();
		Optional<EditLeadDto> editLeadDtoOptional = LeadsDtoMapper.TO_EDITLEAD_DTO.apply(leads);
		assertNotNull(editLeadDtoOptional);
	}

	@Test
	void testToEditLeadDtos() {
		Collection<Leads> leadsCollection = new ArrayList<>();
		List<EditLeadDto> editLeadDtoList = LeadsDtoMapper.TO_EDITLEAD_DTOS.apply(leadsCollection);
		assertNotNull(editLeadDtoList);
	}

	@Test
	void testToQualifyLead() {
		Leads leads = new Leads();
		Optional<QualifyLeadDto> qualifyLeadDtoOptional = LeadsDtoMapper.TO_QUALIFY_LEAD.apply(leads);
		assertNotNull(qualifyLeadDtoOptional);
	}

	@Test
	void testToDashboardLeadDto() {
		Leads leads = new Leads();
		Contacts contacts = new Contacts();
		List<Contacts> con = new ArrayList<>();
		contacts.setPrimary(true);
		leads.setCreatedDate(LocalDateTime.now());
		leads.setLeadId(1);
		con.add(contacts);
		leads.setContacts(con);
		Optional<LeadDashboardDto> leadDashboardDtoOptional = LeadsDtoMapper.TO_DASHBOARD_LEADDTO.apply(leads);
		assertNotNull(leadDashboardDtoOptional);
		assertTrue(leadDashboardDtoOptional.isPresent());
		LeadDashboardDto leadDashboardDto = leadDashboardDtoOptional.get();
		ContactDto contactDto = new ContactDto();
		contactDto.setPrimary(true);
		leadDashboardDto.setPrimaryContact(contactDto);
		assertNotNull(leadDashboardDto.getCreatedOn());
		assertNotNull(leadDashboardDto.getPrimaryContact());
	}
}
