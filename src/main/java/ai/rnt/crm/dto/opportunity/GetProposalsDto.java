package ai.rnt.crm.dto.opportunity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProposalsDto {

	private Integer propId;

	private String genPropId;

	private String ownerName;

	private String currency;

	private String createdBy;
}
