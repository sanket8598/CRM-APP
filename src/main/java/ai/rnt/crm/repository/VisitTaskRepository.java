package ai.rnt.crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.VisitTask;

public interface VisitTaskRepository extends JpaRepository<VisitTask, Integer> {

	List<VisitTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(Date todayAsDate, String time,
			boolean remainderOn);

}
