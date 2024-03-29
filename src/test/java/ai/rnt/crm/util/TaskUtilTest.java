package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.VisitTask;

class TaskUtilTest {

	@Mock
	Logger logger;

	@InjectMocks
	TaskUtil taskUtil;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCheckDuplicateTask() {
		List<PhoneCallTask> allTasks = new ArrayList<>();
		PhoneCallTask phoneCallTask = new PhoneCallTask();
		PhoneCallTask phoneCallTask1 = new PhoneCallTask();
		phoneCallTask.setSubject("Sample Subject");
		phoneCallTask1.setSubject("Sample Subject");
		phoneCallTask.setStatus("Sample Status");
		phoneCallTask1.setStatus("Sample Status");
		phoneCallTask.setPriority("Low");
		phoneCallTask1.setPriority("Low");
		phoneCallTask.setDueDate(LocalDate.now());
		phoneCallTask1.setDueDate(LocalDate.now());
		phoneCallTask.setRemainderVia("Both");
		phoneCallTask1.setRemainderVia("Both");
		phoneCallTask.setRemainderDueAt("10:10");
		phoneCallTask1.setRemainderDueAt("10:10");
		phoneCallTask.setRemainderDueOn(LocalDate.now());
		phoneCallTask1.setRemainderDueOn(LocalDate.now());
		phoneCallTask.setDescription("test");
		phoneCallTask1.setDescription("test");
		phoneCallTask.setDueTime("11:11");
		phoneCallTask1.setDueTime("11:11");
		allTasks.add(phoneCallTask1);
		boolean result = taskUtil.checkDuplicateTask(allTasks, phoneCallTask);
		assertTrue(result, "Expected a duplicate task but did not find one");
	}

	@Test
	void testCompareDatesIgnoringTimeSameDate() {
		LocalDate date1 = LocalDate.of(2022, 10, 15);
		LocalDate date2 = LocalDate.of(2022, 10, 15);
		boolean result = TaskUtil.compareDatesIgnoringTime(date1, date2);
		assertTrue(result, "Expected dates to be equal");
	}

	@Test
	void testCompareDatesIgnoringTimeDifferentDates() {
		LocalDate date1 = LocalDate.of(2022, 10, 15);
		LocalDate date2 = LocalDate.of(2022, 10, 16);
		boolean result = TaskUtil.compareDatesIgnoringTime(date1, date2);
		assertFalse(result, "Expected dates to be different");
	}

	@Test
	void testCheckDuplicateVisitTask() {
		List<VisitTask> allTasks = new ArrayList<>();
		VisitTask visitTask = new VisitTask();
		VisitTask visitTask1 = new VisitTask();
		visitTask.setSubject("Sample Subject");
		visitTask1.setSubject("Sample Subject");
		visitTask.setStatus("Sample Status");
		visitTask1.setStatus("Sample Status");
		visitTask.setPriority("Low");
		visitTask1.setPriority("Low");
		visitTask.setDueDate(LocalDate.now());
		visitTask1.setDueDate(LocalDate.now());
		visitTask.setRemainderVia("Both");
		visitTask1.setRemainderVia("Both");
		visitTask.setRemainderDueAt("10:10");
		visitTask1.setRemainderDueAt("10:10");
		visitTask.setRemainderDueOn(LocalDate.now());
		visitTask1.setRemainderDueOn(LocalDate.now());
		visitTask.setDescription("test");
		visitTask1.setDescription("test");
		visitTask.setDueTime("11:11");
		visitTask1.setDueTime("11:11");
		allTasks.add(visitTask1);
		boolean result = TaskUtil.checkDuplicateVisitTask(allTasks, visitTask);
		assertTrue(result, "Expected a duplicate task but did not find one");
	}

	@Test
	void testCheckDuplicateMeetingTask() {
		List<MeetingTask> allTasks = new ArrayList<>();
		MeetingTask meetingTask = new MeetingTask();
		MeetingTask meetingTask1 = new MeetingTask();
		meetingTask.setSubject("Sample Subject");
		meetingTask1.setSubject("Sample Subject");
		meetingTask.setStatus("Sample Status");
		meetingTask1.setStatus("Sample Status");
		meetingTask.setPriority("Low");
		meetingTask1.setPriority("Low");
		meetingTask.setDueDate(LocalDate.now());
		meetingTask1.setDueDate(LocalDate.now());
		meetingTask.setRemainderVia("Both");
		meetingTask1.setRemainderVia("Both");
		meetingTask.setRemainderDueAt("10:10");
		meetingTask1.setRemainderDueAt("10:10");
		meetingTask.setRemainderDueOn(LocalDate.now());
		meetingTask1.setRemainderDueOn(LocalDate.now());
		meetingTask.setDescription("test");
		meetingTask1.setDescription("test");
		meetingTask.setDueTime("11:11");
		meetingTask1.setDueTime("11:11");
		allTasks.add(meetingTask1);
		boolean result = TaskUtil.checkDuplicateMeetingTask(allTasks, meetingTask);
		assertTrue(result, "Expected a duplicate task but did not find one");
	}

	@Test
	void testCheckDuplicateLeadTask() {
		List<LeadTask> allTasks = new ArrayList<>();
		LeadTask task = new LeadTask();
		LeadTask task1 = new LeadTask();
		task.setSubject("Sample Subject");
		task1.setSubject("Sample Subject");
		task.setStatus("Sample Status");
		task1.setStatus("Sample Status");
		task.setPriority("Low");
		task1.setPriority("Low");
		task.setDueDate(LocalDate.now());
		task1.setDueDate(LocalDate.now());
		task.setRemainderVia("Both");
		task1.setRemainderVia("Both");
		task.setRemainderDueAt("10:10");
		task1.setRemainderDueAt("10:10");
		task.setRemainderDueOn(LocalDate.now());
		task1.setRemainderDueOn(LocalDate.now());
		task.setDescription("test");
		task1.setDescription("test");
		task.setDueTime("11:11");
		task1.setDueTime("11:11");
		allTasks.add(task1);
		boolean result = TaskUtil.checkDuplicateLeadTask(allTasks, task);
		assertTrue(result, "Expected a duplicate task but did not find one");
	}

	@Test
	void testCheckDuplicateOptyTask() {
		List<OpportunityTask> allTasks = new ArrayList<>();
		OpportunityTask task = new OpportunityTask();
		OpportunityTask task1 = new OpportunityTask();
		task.setSubject("Sample Subject");
		task1.setSubject("Sample Subject");
		task.setStatus("Sample Status");
		task1.setStatus("Sample Status");
		task.setPriority("Low");
		task1.setPriority("Low");
		task.setDueDate(LocalDate.now());
		task1.setDueDate(LocalDate.now());
		task.setRemainderVia("Both");
		task1.setRemainderVia("Both");
		task.setRemainderDueAt("10:10");
		task1.setRemainderDueAt("10:10");
		task.setRemainderDueOn(LocalDate.now());
		task1.setRemainderDueOn(LocalDate.now());
		task.setDescription("test");
		task1.setDescription("test");
		task.setDueTime("11:11");
		task1.setDueTime("11:11");
		allTasks.add(task1);
		boolean result = TaskUtil.checkDuplicateOptyTask(allTasks, task);
		assertTrue(result, "Expected a duplicate task but did not find one");
	}
}
