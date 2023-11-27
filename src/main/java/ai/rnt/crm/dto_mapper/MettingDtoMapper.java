package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.MettingDto;
import ai.rnt.crm.entity.Meetings;

public class MettingDtoMapper {

	private MettingDtoMapper() {

	}

	/**
	 * This function will convert MettingDto into optional Mettings Entity. <b>This
	 * function will return null if passed MettingDto is null</b> <br>
	 * <b>Param</b> MettingDto <br>
	 * <b>Return</b> Metting
	 * 
	 * @author Nikhil Gaikwad
	 * @since 25-11-2023
	 * @version 1.0
	 */
	public static final Function<MettingDto, Optional<Meetings>> TO_METTING = e -> evalMapper(e, Meetings.class);
	/**
	 * @since 25-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<MettingDto>, List<Meetings>> TO_METTINGS = e -> e.stream()
			.map(dm -> TO_METTING.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert Mettings Entity into optional MettingDto . <b>This
	 * function will return null if passed MettingDto is null</b> <br>
	 * <b>Param</b> Mettings <br>
	 * <b>Return</b> MettingDto
	 * 
	 * @since 25-11-2023
	 * @Version 1.0
	 */
	public static final Function<Meetings, Optional<MettingDto>> TO_METTING_DTO = e -> evalMapper(e, MettingDto.class);

	/**
	 * @since 25-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Meetings>, List<MettingDto>> TO_METTING_DTOS = e -> e.stream()
			.map(dm -> TO_METTING_DTO.apply(dm).get()).collect(Collectors.toList());

}
