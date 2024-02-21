package ai.rnt.crm.dao.service;

import java.util.Optional;

import ai.rnt.crm.dto.opportunity.OpprtAttachmentDto;
import ai.rnt.crm.entity.OpprtAttachment;

public interface OpprtAttachmentDaoService extends CrudService<OpprtAttachment, OpprtAttachmentDto> {

	public Optional<OpprtAttachment> findById(Integer optAttchId);

	public OpprtAttachment addOpprtAttachment(OpprtAttachment data);

}
