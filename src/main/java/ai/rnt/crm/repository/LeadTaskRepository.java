package ai.rnt.crm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.LeadTask;

public interface LeadTaskRepository extends JpaRepository<LeadTask, Integer> {

	List<LeadTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(LocalDate todayAsDate, String time,
			boolean remainderOn);

}
