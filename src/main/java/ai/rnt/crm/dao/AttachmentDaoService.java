package ai.rnt.crm.dao;

import java.util.Optional;

import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.entity.Attachment;

public interface AttachmentDaoService extends CrudService<Attachment, AttachmentDto> {

	public Attachment addAttachment(Attachment attachment);

	public Optional<Attachment> findById(Integer emailAttchId);
}
