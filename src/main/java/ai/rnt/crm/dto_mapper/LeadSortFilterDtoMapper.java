package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.function.Function;

import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.entity.LeadSortFilter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class LeadSortFilterDtoMapper {

	/**
	 * This function will convert LeadSortFilter into optional LeadSortFilterDto.
	 * <b>This function will return null if passed LeadSortFilter is null</b> <br>
	 * <b>Param</b> LeadSortFilter<br>
	 * <b>Return</b> LeadSortFilterDto
	 * 
	 * @since 11-11-2023
	 * @version 1.0
	 */
	public static final Function<LeadSortFilter, Optional<LeadSortFilterDto>> TO_LEAD_SORT_FILTER_DTO = e -> evalMapper(
			e, LeadSortFilterDto.class);

	/**
	 * This function will convert LeadSortFilterDto into optional LeadSortFilter
	 * entity. <b>This function will return null if passed LeadSortFilterDto is
	 * null</b> <br>
	 * <b>Param</b> LeadSortFilterDto<br>
	 * <b>Return</b> LeadSortFilter
	 * 
	 * @since 11-11-2023
	 * @version 1.0
	 */
	public static final Function<LeadSortFilterDto, Optional<LeadSortFilter>> TO_LEAD_SORT_FILTER = e -> evalMapper(e,
			LeadSortFilter.class);
}
