package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

import java.util.EnumMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

	private final ContactDaoService contactDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addContact(@Valid ContactDto contactDto, Integer leadId) {
		EnumMap<ApiResponse, Object> contactMap = new EnumMap<>(ApiResponse.class);
		try {
			contactMap.put(SUCCESS, true);
			List<Contacts> existingContacts = contactDaoService.contactsOfLead(leadId);
			CompanyMaster company = existingContacts.stream().filter(Contacts::getPrimary)
					.map(Contacts::getCompanyMaster).findFirst().orElse(null);
			boolean isPrimary = existingContacts.stream().anyMatch(Contacts::getPrimary);
			Contacts contact = TO_CONTACT.apply(contactDto).orElseThrow(ResourceNotFoundException::new);
			contact.setCompanyMaster(company);
			if (isPrimary || TRUE.equals(contact.getPrimary()))
				existingContacts.stream().filter(Contacts::getPrimary).forEach(con -> {
					con.setPrimary(false);
					contactDaoService.addContact(con);
				});
			if (nonNull(contactDaoService.addContact(contact)))
				contactMap.put(MESSAGE, "Contact Added Successfully!!");
			else {
				contactMap.put(SUCCESS, false);
				contactMap.put(MESSAGE, "Contact Not Added!!");
			}
			return new ResponseEntity<>(contactMap, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding the contact for a lead...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getContact(Integer contactId) {
		EnumMap<ApiResponse, Object> contactMap = new EnumMap<>(ApiResponse.class);
		try {
			contactMap.put(SUCCESS, true);
			contactMap.put(DATA, TO_CONTACT_DTO.apply(contactDaoService.findById(contactId)
					.orElseThrow(() -> new ResourceNotFoundException("Contact", "contactId", contactId))));
			return new ResponseEntity<>(contactMap, FOUND);
		} catch (Exception e) {
			log.error("error occured while getting the contact of a lead...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateContact(ContactDto contactDto, Integer contactId) {
		EnumMap<ApiResponse, Object> contactMap = new EnumMap<>(ApiResponse.class);
		try {
			contactMap.put(SUCCESS, true);
			Contacts contact = contactDaoService.findById(contactId)
					.orElseThrow(() -> new ResourceNotFoundException("Contact", "contactId", contactId));
			contact.setFirstName(contactDto.getFirstName());
			contact.setLastName(contactDto.getLastName());
			contact.setContactNumberPrimary(contactDto.getContactNumberPrimary());
			contact.setContactNumberSecondary(contactDto.getContactNumberSecondary());
			contact.setDesignation(contactDto.getDesignation());
			contact.setWorkEmail(contactDto.getWorkEmail());
			contact.setLinkedinId(contactDto.getLinkedinId());
			contact.setPrimary(contactDto.getPrimary());
			contact.setBusinessCard(contactDto.getBusinessCard());
			contact.setBusinessCardName(contactDto.getBusinessCardName());
			contact.setBusinessCardType(contactDto.getBusinessCardType());
			if (nonNull(contactDaoService.addContact(contact)))
				contactMap.put(MESSAGE, "Contact Updated Successfully!!");
			else {
				contactMap.put(SUCCESS, false);
				contactMap.put(MESSAGE, "Contact Not Updated!!");
			}
			return new ResponseEntity<>(contactMap, CREATED);
		} catch (Exception e) {
			log.error("error occured while updating the contact of a lead...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

}
