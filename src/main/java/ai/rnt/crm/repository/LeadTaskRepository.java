package ai.rnt.crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.LeadTask;

public interface LeadTaskRepository extends JpaRepository<LeadTask, Integer> {

	List<LeadTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(Date todayAsDate, String time,
			boolean remainderOn);

}
