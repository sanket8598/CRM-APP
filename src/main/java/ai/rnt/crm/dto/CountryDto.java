package ai.rnt.crm.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer countryId;

	@NotBlank(message = "country should not be null or empty!!")
	@Size(max = 100,message = "Country shouldn't be greater than {max} characters!!")
	private String country;
	
	private String countryCode;
	
	private CurrencyDto currency;

}
