package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.EmailDto;
import ai.rnt.crm.entity.AddEmail;

public class EmailDtoMapper {

	EmailDtoMapper() {

	}

	/**
	 * This function will convert EmailDto into optional AddEmail Entity. <b>This
	 * function will return null if passed EmailDto is null</b> <br>
	 * <b>Param</b> EmailDto <br>
	 * <b>Return</b> AddEmail
	 * 
	 * @author Nikhil Gaikwad
	 * @since 12-09-2023
	 * @version 1.0
	 */
	public static final Function<EmailDto, Optional<AddEmail>> TO_EMAIL = e -> evalMapper(e, AddEmail.class);
	/**
	 * @since 12-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<EmailDto>, List<AddEmail>> TO_EMAILS = e -> e.stream()
			.map(dm -> TO_EMAIL.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddEmail Entity into optional EmailDto . <b>This
	 * function will return null if passed EmailDto is null</b> <br>
	 * <b>Param</b> AddEmail <br>
	 * <b>Return</b> EmailDto
	 * 
	 * @since 12-09-2023
	 * @Version 1.0
	 */
	public static final Function<AddEmail, Optional<EmailDto>> TO_EMAIL_DTO = e -> evalMapper(e, EmailDto.class);

	/**
	 * @since 12-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<AddEmail>, List<EmailDto>> TO_EMAIL_DTOS = e -> e.stream()
			.map(dm -> TO_EMAIL_DTO.apply(dm).get()).collect(Collectors.toList());

}
