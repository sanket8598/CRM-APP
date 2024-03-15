package ai.rnt.crm.functional.custominterface.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.LeadsCardDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.ServiceFallsMaster;

class LeadsCardMapperImplTest {

	@Test
    void mapLeadToLeadsCardDto_WithValidLeadAndFields_ReturnsMappedDto() {
        Leads lead = mock(Leads.class);
        String primaryField = "Lead Name";
        String secondaryField = "Topic";
        Contacts contacts = mock(Contacts.class);
        contacts.setFirstName("xyz");
        contacts.setLastName("xyz");
        LeadsCardMapperImpl mapper = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto = mapper.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto.getLeadId());
        assertEquals(lead.getDisqualifyAs(), leadsCardDto.getDisqualifyAs());
        assertEquals(lead.getStatus(), leadsCardDto.getStatus());
        assertEquals(lead.getImportant(), leadsCardDto.isImportant());
        CompanyMaster cmp=mock(CompanyMaster.class);
        cmp.setCompanyName(secondaryField);
        contacts=new Contacts();
        contacts.setCompanyMaster(cmp);
        secondaryField = "Lead Name";
        primaryField = "Company Name";
        LeadsCardMapperImpl mapper1 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto1 = mapper1.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto1.getLeadId());
        contacts=new Contacts();
        contacts.setCompanyMaster(cmp);
        secondaryField = "Company Name";
        LeadsCardMapperImpl mapper2 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto2 = mapper2.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto2.getLeadId());
        contacts=new Contacts();
        contacts.setCompanyMaster(cmp);
        secondaryField = "Designation";
        LeadsCardMapperImpl mapper3 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto3 = mapper3.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto3.getLeadId());
        secondaryField = "Budget Amount";
        LeadsCardMapperImpl mapper4 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto4 = mapper4.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto4.getLeadId());
        secondaryField = "Service Falls Into";
        ServiceFallsMaster sf=new ServiceFallsMaster();
        sf.setServiceName(secondaryField);
        lead.setServiceFallsMaster(sf);
        LeadsCardMapperImpl mapper5 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto5 = mapper5.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto5.getLeadId());
        secondaryField = "Lead Source";
        LeadSourceMaster ls=new LeadSourceMaster();
        ls.setSourceName(secondaryField);
        lead.setLeadSourceMaster(ls);
        LeadsCardMapperImpl mapper6 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto6 = mapper6.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto6.getLeadId());
        secondaryField = "Lead Assign Username";
        EmployeeMaster emp=new EmployeeMaster();
        emp.setFirstName(secondaryField);
        emp.setLastName(secondaryField);
        lead.setEmployee(emp);
        LeadsCardMapperImpl mapper8 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto8 = mapper8.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto8.getLeadId());
        secondaryField = "Domain";
        DomainMaster dmn=new DomainMaster();
        dmn.setDomainName(secondaryField);
        lead.setDomainMaster(dmn);
        LeadsCardMapperImpl mapper9 = new LeadsCardMapperImpl();
        LeadsCardDto leadsCardDto9 = mapper9.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
        assertEquals(lead.getLeadId(), leadsCardDto9.getLeadId());
    }
	
	@Test
	void mapLeadToLeadsCardDto_WithInvalidFields_ReturnsMappedDtoWithDefaultFields() {
	    Leads lead =mock(Leads.class);
	    String primaryField = "ame";
	    String secondaryField = "sdf";
	    Contacts contacts = mock(Contacts.class);
	    contacts.setFirstName("xyz");
        contacts.setLastName("xyz");
	    LeadsCardMapperImpl mapper = new LeadsCardMapperImpl();
	    LeadsCardDto leadsCardDto = mapper.mapLeadToLeadsCardDto(lead, primaryField, secondaryField, contacts);
	    assertNotNull(leadsCardDto);
	}


}
