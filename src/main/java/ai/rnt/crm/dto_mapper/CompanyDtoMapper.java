package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CompanyMaster;

public class CompanyDtoMapper {
	CompanyDtoMapper() {
	}

	/**
	 * This function will convert CompanyDto into optional Company Entity. <b>This
	 * function will return null if passed LeadDto is null</b> <br>
	 * <b>Param</b> CompanyDto <br>
	 * <b>Return</b> Company
	 * 
	 * @since 04-09-2023
	 * @version 1.0
	 */
	public static final Function<CompanyDto, Optional<CompanyMaster>> TO_COMPANY = e -> evalMapper(e,
			CompanyMaster.class);
	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CompanyDto>, List<CompanyMaster>> TO_COMPANYS = e -> e.stream()
			.map(dm -> TO_COMPANY.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert Company Entity into optional CompanyDto . <b>This
	 * function will return null if passed Company is null</b> <br>
	 * <b>Param</b> Company <br>
	 * <b>Return</b> CompanyDto
	 * 
	 * @since 04-09-2023
	 * @Version 1.0
	 */
	public static final Function<CompanyMaster, Optional<CompanyDto>> TO_COMPANY_DTO = e -> evalMapper(e,
			CompanyDto.class);

	/**
	 * @since 04-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CompanyMaster>, List<CompanyDto>> TO_COMPANY_DTOS = e -> e.stream()
			.map(dm -> TO_COMPANY_DTO.apply(dm).get()).collect(Collectors.toList());

}
