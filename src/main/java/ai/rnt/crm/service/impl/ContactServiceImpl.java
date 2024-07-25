package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.ContactUtil.isDuplicateContact;
import static ai.rnt.crm.util.StringUtil.hasWhitespace;
import static ai.rnt.crm.util.StringUtil.splitByWhitespace;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.ContactService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
	private final LeadDaoService leadDaoService;
	private final ContactDaoService contactDaoService;
	private final AuditAwareUtil auditAwareUtil;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addContact(ContactDto contactDto, Integer leadId) {
		log.info("inside the addContact method...{}", leadId);
		EnumMap<ApiResponse, Object> contactMap = new EnumMap<>(ApiResponse.class);
		try {
			contactMap.put(SUCCESS, true);
			List<Contacts> existingContacts = contactDaoService.contactsOfLead(leadId);
			CompanyMaster company = existingContacts.stream().filter(Contacts::getPrimary)
					.map(Contacts::getCompanyMaster).findFirst().orElse(null);
			Contacts contact = TO_CONTACT.apply(contactDto).orElseThrow(ResourceNotFoundException::new);
			if (nonNull(contactDto.getName())) {
				String[] names = splitByWhitespace(contactDto.getName());
				if (hasWhitespace(contactDto.getName()) && names.length == 2) {
					contact.setFirstName(nonNull(names[0]) ? names[0] : "");
					contact.setLastName("null".equalsIgnoreCase(names[1]) ? null : names[1]);
				} else {
					contact.setFirstName(nonNull(contactDto.getName()) ? contactDto.getName() : "");
					contact.setLastName(null);
				}
			}
			contact.setCompanyMaster(company);
			leadDaoService.getLeadById(leadId).ifPresent(contact::setLead);
			if (TRUE.equals(contact.getPrimary()) && existingContacts.stream().anyMatch(Contacts::getPrimary))
				existingContacts.stream().filter(Contacts::getPrimary).forEach(con -> {
					con.setPrimary(false);
					contactDaoService.addContact(con);
				});
			if (!isDuplicateContact(existingContacts, contact) && nonNull(contactDaoService.addContact(contact)))
				contactMap.put(MESSAGE, "Contact Added Successfully !!");
			else {
				contactMap.put(SUCCESS, false);
				contactMap.put(MESSAGE, "Contact Already Exist !!");
			}
			return new ResponseEntity<>(contactMap, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding the contact for a lead...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getContact(Integer contactId) {
		log.info("inside the getContact method...{}", contactId);
		EnumMap<ApiResponse, Object> getContactMap = new EnumMap<>(ApiResponse.class);
		try {
			getContactMap.put(SUCCESS, true);
			getContactMap.put(DATA, TO_CONTACT_DTO.apply(contactDaoService.findById(contactId)
					.orElseThrow(() -> new ResourceNotFoundException("Contact", "contactId", contactId))));
			return new ResponseEntity<>(getContactMap, OK);
		} catch (Exception e) {
			log.error("error occured while getting the contact of a lead...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateContact(ContactDto contactDto, Integer contactId) {
		log.info("inside the updateContact method...{}", contactId);
		EnumMap<ApiResponse, Object> updateContactMap = new EnumMap<>(ApiResponse.class);
		try {
			updateContactMap.put(SUCCESS, true);
			Contacts contact = contactDaoService.findById(contactId)
					.orElseThrow(() -> new ResourceNotFoundException("Contact", "contactId", contactId));
			if (nonNull(contactDto.getName())) {
				String[] names = splitByWhitespace(contactDto.getName());
				if (hasWhitespace(contactDto.getName()) && names.length == 2) {
					contact.setFirstName(nonNull(names[0]) ? names[0] : "");
					contact.setLastName("null".equalsIgnoreCase(names[1]) ? null : names[1]);
				} else {
					contact.setFirstName(contactDto.getName());
					contact.setLastName(null);
				}
			}
			contact.setContactNumberPrimary(contactDto.getContactNumberPrimary());
			contact.setContactNumberSecondary(contactDto.getContactNumberSecondary());
			contact.setDesignation(contactDto.getDesignation());
			contact.setWorkEmail(contactDto.getWorkEmail());
			contact.setLinkedinId(contactDto.getLinkedinId());
			contact.setBusinessCard(contactDto.getBusinessCard());
			contact.setBusinessCardName(contactDto.getBusinessCardName());
			contact.setBusinessCardType(contactDto.getBusinessCardType());

			List<Contacts> existingContacts = contactDaoService.contactsOfLead(contact.getLead().getLeadId());
			boolean isPrimary = existingContacts.stream().anyMatch(Contacts::getPrimary);
			Optional<Contacts> primaryContact = existingContacts.stream().filter(Contacts::getPrimary).findFirst();
			if (TRUE.equals(contactDto.getPrimary()) && isPrimary)
				existingContacts.stream().filter(Contacts::getPrimary).forEach(con -> {
					con.setPrimary(false);
					contactDaoService.addContact(con);
				});
			else if (FALSE.equals(contactDto.getPrimary()) && (existingContacts.size() == 1
					|| (primaryContact.isPresent() && primaryContact.get().getContactId().equals(contactId)))) {
				updateContactMap.put(SUCCESS, false);
				updateContactMap.put(MESSAGE, "Cannot unmark the only contact as primary !!");
				return new ResponseEntity<>(updateContactMap, BAD_REQUEST);
			}
			contact.setPrimary(contactDto.getPrimary());
			if (nonNull(contactDaoService.addContact(contact)))
				updateContactMap.put(MESSAGE, "Contact Updated Successfully !!");
			else {
				updateContactMap.put(SUCCESS, false);
				updateContactMap.put(MESSAGE, "Contact Not Updated !!");
			}
			return new ResponseEntity<>(updateContactMap, OK);
		} catch (Exception e) {
			log.error("error occured while updating the contact of a lead...{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteContact(Integer contactId) {
		log.info("inside the deleteContact method...{}", contactId);
		EnumMap<ApiResponse, Object> deleteContactMap = new EnumMap<>(ApiResponse.class);
		try {
			deleteContactMap.put(SUCCESS, true);
			Contacts contact = contactDaoService.findById(contactId)
					.orElseThrow(() -> new ResourceNotFoundException("Contact", "contactId", contactId));
			if (TRUE.equals(contact.getPrimary()))
				deleteContactMap.put(MESSAGE, "Can't Delete,Ensure it's not a primary contact !!");
			else {
				contact.setDeletedBy(auditAwareUtil.getLoggedInStaffId());
				contact.setDeletedDate(now());
				if (nonNull(contactDaoService.addContact(contact)))
					deleteContactMap.put(MESSAGE, "Contact Deleted Successfully !!");
				else {
					deleteContactMap.put(SUCCESS, false);
					deleteContactMap.put(MESSAGE, "Contact Not Deleted !!");
				}
			}
			return new ResponseEntity<>(deleteContactMap, OK);
		} catch (Exception e) {
			log.error("error occured while deleting the contact of a lead...{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
