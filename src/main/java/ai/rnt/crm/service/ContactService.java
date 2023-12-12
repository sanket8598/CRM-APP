package ai.rnt.crm.service;

import java.util.EnumMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.enums.ApiResponse;

public interface ContactService {

	ResponseEntity<EnumMap<ApiResponse, Object>> addContact(@Valid ContactDto contactDto, Integer leadId);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateContact(ContactDto contactDto, Integer contactId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getContact(Integer contactId);

}
