package ai.rnt.crm.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer cityId;

	@NotBlank(message = "city should not be null or empty!!")
	private String city;

	@NotNull(message = "state Id should not be null!!")
	private StateDto state;
}
