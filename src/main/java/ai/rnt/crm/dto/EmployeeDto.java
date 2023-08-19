package ai.rnt.crm.dto;

import java.util.ArrayList;
import java.util.List;

import ai.rnt.crm.entity.RoleMaster;
import lombok.Data;

@Data
public class EmployeeDto {

	private Integer staffId;

	private String userID;
	private String password;

	private String firstName; // first_name

	private String middleName; // middle_name

	private String lastName;

	private String emailID;

	private String employeeJobTitle;

	private List<RoleMaster> employeeRole = new ArrayList<>();

}
