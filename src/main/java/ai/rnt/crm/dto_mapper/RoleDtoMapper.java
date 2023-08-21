package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.Role;
import ai.rnt.crm.entity.RoleMaster;

public class RoleDtoMapper {

	RoleDtoMapper(){}
	/** 
	 * This function will convert RoleMaster Entity into Role DTO.
	 * <b>This function will return null if passed RoleMaster is null</b>
	 * <br><b>Param</b> RoleMaster 
	 * <br><b>Return</b> Role
	 * @since version 1.0 
	 */
	public static final Function<RoleMaster, Optional<Role>> TO_Role = e -> {
		return evalMapper(e, Role.class);
		};
	/**
	 * @since 21-08-2023
	 * @version 1.3
	 *
	 */
	public static final Function<Collection<RoleMaster>, List<Role>> TO_Roles= e ->{
		return e.stream().map(dm -> TO_Role.apply(dm).get()).collect(Collectors.toList());
	};
}
