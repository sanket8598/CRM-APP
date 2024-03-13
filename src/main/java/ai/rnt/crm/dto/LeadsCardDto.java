package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadsCardDto {

	private Integer leadId;

	private String shortName;

	private String disqualifyAs;

	private String status;

	private boolean important;

	private String primaryField;

	private String secondaryField;

}
