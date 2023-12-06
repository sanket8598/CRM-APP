package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.PhoneCallTask;

public interface CallTaskRepository extends JpaRepository<PhoneCallTask, Integer> {
}
