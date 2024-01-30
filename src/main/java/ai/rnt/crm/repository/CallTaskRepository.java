package ai.rnt.crm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.PhoneCallTask;

public interface CallTaskRepository extends JpaRepository<PhoneCallTask, Integer> {

	List<PhoneCallTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(LocalDate todayDate, String time,
			boolean remainderOn);
}
