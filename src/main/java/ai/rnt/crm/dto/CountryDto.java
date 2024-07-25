package ai.rnt.crm.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer countryId;

	@NotBlank(message = "country should not be null or empty!!")
	private String country;
	
	private String countryCode;
	
	private CurrencyDto currency;

}
