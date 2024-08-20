package ai.rnt.crm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.OpportunityTask;

public interface OpportunityTaskRepository extends JpaRepository<OpportunityTask, Integer> {

	List<OpportunityTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(LocalDate todayAsDate, String time,
			boolean remainderOn);

}
