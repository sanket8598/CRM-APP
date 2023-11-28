package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.entity.Visit;

public class VisitDtoMapper {

	VisitDtoMapper() {

	}

	/**
	 * This function will convert VisitDto into optional Visit Entity. <b>This
	 * function will return null if passed CountryDto is null</b> <br>
	 * <b>Param</b> VisitDto <br>
	 * <b>Return</b> Visit
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<VisitDto, Optional<Visit>> TO_VISIT = e -> evalMapper(e, Visit.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<VisitDto>, List<Visit>> TO_VISITS = e -> e.stream()
			.map(dm -> TO_VISIT.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert Visit Entity into optional VisitDto . <b>This
	 * function will return null if passed Company is null</b> <br>
	 * <b>Param</b> Visit <br>
	 * <b>Return</b> VisitDto
	 * 
	 * @author Nikhil Gaikwad
	 * @since 14-09-2023
	 * @Version 1.0
	 */
	public static final Function<Visit, Optional<VisitDto>> TO_VISIT_DTO = e -> evalMapper(e, VisitDto.class);

	/**
	 * @since 14-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Visit>, List<VisitDto>> TO_VISIT_DTOS = e -> e.stream()
			.map(dm -> TO_VISIT_DTO.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert Visit Entity into optional EditVisitDto . <b>This
	 * function will return null if passed Visit is null</b> <br>
	 * <b>Param</b> Visit <br>
	 * <b>Return</b> EditVisitDto
	 * 
	 * @since 27-10-2023
	 * @Version 1.0
	 */
	public static final Function<Visit, Optional<EditVisitDto>> TO_EDIT_VISIT_DTO = e -> evalMapper(e,
			EditVisitDto.class);

	/**
	 * @since 27-10-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Visit>, List<EditVisitDto>> TO_EDIT_VISIT_DTOS = e -> e.stream()
			.map(dm -> TO_EDIT_VISIT_DTO.apply(dm).get()).collect(Collectors.toList());
}
