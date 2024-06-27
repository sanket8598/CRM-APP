package ai.rnt.crm.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer stateId;

	@NotBlank(message = "state should not be null or empty!!")
	private String state;

	@NotNull(message = "country Id should not be null!!")
	private CountryDto country;
}
