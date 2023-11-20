package ai.rnt.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadSortFilterDto {
	
	private Integer leadSortFilterId;
	
	private String primaryFilter;
	
	private String secondaryFilter;

    EmployeeDto employee;
	

}
