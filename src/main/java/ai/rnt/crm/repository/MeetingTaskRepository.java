package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.MeetingTask;

public interface MeetingTaskRepository extends JpaRepository<MeetingTask, Integer> {

}
