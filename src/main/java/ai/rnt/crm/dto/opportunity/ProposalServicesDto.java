package ai.rnt.crm.dto.opportunity;

import ai.rnt.crm.dto.ServiceFallsDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalServicesDto {

	private ServiceFallsDto serviceFallsMaster;

	private String servicePrice;
}
