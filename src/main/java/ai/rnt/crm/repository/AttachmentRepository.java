package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer>{

}
