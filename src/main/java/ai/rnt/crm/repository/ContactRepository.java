package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Contacts;

public interface ContactRepository extends JpaRepository<Contacts, Integer>{

	List<Contacts> findByLeadLeadIdOrderByCreatedDate(Integer leadId);

}
