package ai.rnt.crm.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer cityId;

	@NotBlank(message = "city should not be null or empty!!")
	@Size(max = 100,message = "City shouldn't be greater than {max} characters!!")
	private String city;

	@NotNull(message = "state Id should not be null!!")
	private StateDto state;
}
