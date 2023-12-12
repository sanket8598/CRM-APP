package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.DomainMasterDto;
import ai.rnt.crm.entity.DomainMaster;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class DomainMasterDtoMapper {

	/**
	 * This function will convert DomainMaster Entity into DomainMasterDto. <b>This
	 * function will return null if passed DomainMaster is null</b> <br>
	 * <b>Param</b> DomainMaster <br>
	 * <b>Return</b> DomainMasterDto
	 * 
	 * @since 15-09-2023
	 * @version 1.0
	 */
	public static final Function<DomainMaster, Optional<DomainMasterDto>> TO_DOMAIN_DTO = e -> evalMapper(e,
			DomainMasterDto.class);

	/**
	 * This function will convert DomainMaster Entity List is converted into list of
	 * DomainMasterDto. <b>This function will return null if passed DomainMaster is
	 * null</b> <br>
	 * <b>Param</b> DomainMaster <br>
	 * <b>Return</b> DomainMasterDto
	 * 
	 * @since 15-09-2023
	 * @version 1.0
	 */
	public static final Function<Collection<DomainMaster>, List<DomainMasterDto>> TO_DOMAIN_DTOS = e -> {
		return e.stream().map(dm -> TO_DOMAIN_DTO.apply(dm).get()).collect(Collectors.toList());
	};

	public static final Function<DomainMasterDto, Optional<DomainMaster>> TO_DOMAIN = e -> evalMapper(e,
			DomainMaster.class);
}
