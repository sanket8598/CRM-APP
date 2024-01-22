package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.function.Function;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.Contacts;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ContactDtoMapper {

	public static final Function<Contacts, Optional<ContactDto>> TO_CONTACT_DTO = e -> evalMapper(e, ContactDto.class);

	public static final Function<ContactDto, Optional<Contacts>> TO_CONTACT = e -> evalMapper(e, Contacts.class);

}
