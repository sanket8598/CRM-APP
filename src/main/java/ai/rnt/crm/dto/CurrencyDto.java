package ai.rnt.crm.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto implements Serializable {

	private static final long serialVersionUID = 3983921282008902526L;
	
	private Integer currencyId;
	
	private String currencySymbol;

}
