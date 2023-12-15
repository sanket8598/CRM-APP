package ai.rnt.crm.api.restcontroller;

import static ai.rnt.crm.constants.ApiConstants.CONTACT;
import static ai.rnt.crm.constants.ApiConstants.CONTACT_ID;
import static ai.rnt.crm.constants.ApiConstants.CREATE_CONTACT;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @author Sanket Wakankar
 * @since 09/12/2023.
 * @version 1.0
 *
 */
@RestController
@RequestMapping(CONTACT)
@RequiredArgsConstructor
@Tag(name = "Contacts", description = "This Section Gives Us The API Endpoint Related To The Contacts")
@Validated
public class ContactsController {

	private final ContactService contactService;

	@PostMapping(CREATE_CONTACT)
	public ResponseEntity<EnumMap<ApiResponse, Object>> createContact(@RequestBody @Valid ContactDto contactDto,
			@PathVariable Integer leadId) {
		return contactService.addContact(contactDto, leadId);
	}

	@GetMapping(CONTACT_ID)
	public ResponseEntity<EnumMap<ApiResponse, Object>> findContact(@PathVariable Integer contactId) {
		return contactService.getContact(contactId);
	}

	@PutMapping(CONTACT_ID)
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateContact(@RequestBody @Valid ContactDto contactDto,
			@PathVariable Integer contactId) {
		return contactService.updateContact(contactDto, contactId);
	}
}
