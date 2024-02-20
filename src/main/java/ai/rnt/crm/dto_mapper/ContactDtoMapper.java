package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.dto.opportunity.OpprtAttachmentDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.OpprtAttachment;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ContactDtoMapper {

	public static final Function<Contacts, Optional<ContactDto>> TO_CONTACT_DTO = e -> evalMapper(e, ContactDto.class);

	public static final Function<ContactDto, Optional<Contacts>> TO_CONTACT = e -> evalMapper(e, Contacts.class);

	public static final Function<Collection<Contacts>, List<ContactDto>> TO_CONTACT_DTOS = e -> e.stream()
			.map(dm -> TO_CONTACT_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<OpprtAttachment, Optional<OpprtAttachmentDto>> TO_OPTY_ATTACHMENT_DTO = e -> evalMapper(e,
			OpprtAttachmentDto.class);

	public static final Function<Collection<OpprtAttachment>, List<OpprtAttachmentDto>> TO_OPTY_ATTACHMENT_DTOS = e -> e
			.stream().map(dm -> TO_OPTY_ATTACHMENT_DTO.apply(dm).get()).collect(Collectors.toList());

}
