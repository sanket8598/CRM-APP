package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Optional;
import java.util.function.Function;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.Contacts;

public class ContactDtoMapper {
	
	public static final Function<Contacts, Optional<ContactDto>> TO_CONTACT_DTO = e -> evalMapper(e, ContactDto.class);

}
