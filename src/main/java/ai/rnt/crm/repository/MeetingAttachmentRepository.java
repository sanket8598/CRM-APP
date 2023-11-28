package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.MeetingAttachments;

public interface MeetingAttachmentRepository extends JpaRepository<MeetingAttachments, Integer> {

}
