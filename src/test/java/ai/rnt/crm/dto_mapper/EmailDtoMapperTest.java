package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.Email;

class EmailDtoMapperTest {

	@Test
	void testToEmail() {
		EmailDto emailDto = new EmailDto();
		Function<EmailDto, Optional<Email>> mapper = EmailDtoMapper.TO_EMAIL;
		Optional<Email> result = mapper.apply(emailDto);
		assertTrue(result.isPresent());
	}

	@Test
	void testToEmails() {
		List<EmailDto> emailDtoList = new ArrayList<>();
		EmailDto emailDto1 = new EmailDto();
		EmailDto emailDto2 = new EmailDto();
		emailDtoList.add(emailDto1);
		emailDtoList.add(emailDto2);
		Function<Collection<EmailDto>, List<Email>> mapper = EmailDtoMapper.TO_EMAILS;
		List<Email> result = mapper.apply(emailDtoList);
		assertEquals(2, result.size());
	}

	@Test
	void testToEmailDto() {
		Email email = new Email();
		Function<Email, Optional<EmailDto>> mapper = EmailDtoMapper.TO_EMAIL_DTO;
		Optional<EmailDto> result = mapper.apply(email);
		assertTrue(result.isPresent());
	}

	@Test
	void testToEmailDtos() {
		List<Email> emailList = new ArrayList<>();
		Email email1 = new Email();
		Email email2 = new Email();
		emailList.add(email1);
		emailList.add(email2);
		Function<Collection<Email>, List<EmailDto>> mapper = EmailDtoMapper.TO_EMAIL_DTOS;
		List<EmailDto> result = mapper.apply(emailList);
		assertEquals(2, result.size());
	}
}
