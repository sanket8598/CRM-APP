package ai.rnt.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    private Integer companyId;
    
	private String companyName;
	
	private String companyWebsite;
	
	private String addressLineOne;
	
	private CountryDto country;
	
	private StateDto state;
	
	private CityDto city;
	
	private String zipCode;
}
