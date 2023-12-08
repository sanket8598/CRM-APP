package ai.rnt.crm.dto_mapper;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static ai.rnt.crm.dto_mapper.RoleDtoMapper.TO_Roles;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;

public class EmployeeToDtoMapper {
	
	EmployeeToDtoMapper(){}
	/** 
	 * This function will convert EmployeeMaster Entity into EmployeeDto.
	 * <b>This function will return null if passed EmployeeMaster is null</b>
	 * <br><b>Param</b> EmployeeMaster 
	 * <br><b>Return</b> EmployeeDto
	 * @since 15-09-2023
	 * @version 1.0 
	 */
	public static final Function<EmployeeMaster, Optional<EmployeeDto>> TO_EMPLOYEE = e -> {
		EmployeeDto employee = evalMapper(e, EmployeeDto.class).get();
		employee.setEmployeeRole(TO_Roles.apply(e.getEmployeeRole()));
		return Optional.of(employee);
		};
	/**
	 * @since 15-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<EmployeeMaster>, Collection<EmployeeDto>> TO_Employees = e ->{
		return e.stream().map(dm -> TO_EMPLOYEE.apply(dm).get()).collect(Collectors.toList());
	};
	
	public static final Function<EmployeeDto, Optional<EmployeeMaster>> TO_EmployeeMaster = e -> evalMapper(e, EmployeeMaster.class);

}
