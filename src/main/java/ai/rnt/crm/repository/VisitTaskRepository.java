package ai.rnt.crm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.VisitTask;

public interface VisitTaskRepository extends JpaRepository<VisitTask, Integer> {

	List<VisitTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(LocalDate todayAsDate, String time,
			boolean remainderOn);

}
