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
	 * @since version 1.0 
	 */
	public static final Function<EmployeeMaster, Optional<EmployeeDto>> TO_Employee = e -> {
		EmployeeDto employee = evalMapper(e, EmployeeDto.class).get();
		employee.setEmployeeRole(TO_Roles.apply(e.getEmployeeRole()));
		return Optional.of(employee);
		};
	/**
	 * @since 21-08-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<EmployeeMaster>, Collection<EmployeeDto>> TO_Employees = e ->{
		return e.stream().map(dm -> TO_Employee.apply(dm).get()).collect(Collectors.toList());
	};

}
