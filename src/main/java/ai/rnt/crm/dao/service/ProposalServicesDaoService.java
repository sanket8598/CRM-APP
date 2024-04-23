package ai.rnt.crm.dao.service;

import java.util.Optional;

import ai.rnt.crm.dto.opportunity.ProposalServicesDto;
import ai.rnt.crm.entity.ProposalServices;

public interface ProposalServicesDaoService extends CrudService<ProposalServices, ProposalServicesDto> {

	Optional<ProposalServices> findById(Integer propServiceId);

}
