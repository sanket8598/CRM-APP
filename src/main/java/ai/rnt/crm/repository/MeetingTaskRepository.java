package ai.rnt.crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.MeetingTask;

public interface MeetingTaskRepository extends JpaRepository<MeetingTask, Integer> {

	List<MeetingTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(Date todayAsDate, String time,
			boolean remainderOn);

}
