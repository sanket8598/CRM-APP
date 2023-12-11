package ai.rnt.crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.PhoneCallTask;

public interface CallTaskRepository extends JpaRepository<PhoneCallTask, Integer> {

	List<PhoneCallTask> findByRemainderDueOnAndRemainderDueAtAndRemainderOn(Date todayDate, String time,
			boolean remainderOn);
}
