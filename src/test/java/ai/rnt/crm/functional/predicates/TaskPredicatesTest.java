package ai.rnt.crm.functional.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.MainTaskDto;

class TaskPredicatesTest {
	
	@Test
    void testNotStartedTaskWithNullStatus() {
        MainTaskDto task = new MainTaskDto();
        assertFalse(TaskPredicates.NOT_STARTED_TASK.test(task),
                "Predicate should return false for task with null status.");
    }

    @Test
    void testNotStartedTaskWithNotStartedStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("Not Started");
        assertTrue(TaskPredicates.NOT_STARTED_TASK.test(task),
                "Predicate should return true for task with 'Not Started' status.");
    }

    @Test
    void testNotStartedTaskWithInProgressStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("In Progress");
        assertFalse(TaskPredicates.NOT_STARTED_TASK.test(task),
                "Predicate should return false for task with 'In Progress' status.");
    }

    @Test
    void testInProgressTaskWithNullStatus() {
        MainTaskDto task = new MainTaskDto();
        assertFalse(TaskPredicates.IN_PROGRESS_TASK.test(task),
                "Predicate should return false for task with null status.");
    }

    @Test
    void testInProgressTaskWithInProgressStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("In Progress");
        assertTrue(TaskPredicates.IN_PROGRESS_TASK.test(task),
                "Predicate should return true for task with 'In Progress' status.");
    }

    @Test
    void testInProgressTaskWithNotStartedStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("Not Started");
        assertFalse(TaskPredicates.IN_PROGRESS_TASK.test(task),
                "Predicate should return false for task with 'Not Started' status.");
    }

    @Test
    void testOnHoldTaskWithNullStatus() {
        MainTaskDto task = new MainTaskDto();
        assertFalse(TaskPredicates.ON_HOLD_TASK.test(task),
                "Predicate should return false for task with null status.");
    }

    @Test
    void testOnHoldTaskWithOnHoldStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("On Hold");
        assertTrue(TaskPredicates.ON_HOLD_TASK.test(task),
                "Predicate should return true for task with 'On Hold' status.");
    }

    @Test
    void testOnHoldTaskWithInProgressStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("In Progress");
        assertFalse(TaskPredicates.ON_HOLD_TASK.test(task),
                "Predicate should return false for task with 'In Progress' status.");
    }

    @Test
    void testCompletedTaskWithNullStatus() {
        MainTaskDto task = new MainTaskDto();
        assertFalse(TaskPredicates.COMPLETED_TASK.test(task),
                "Predicate should return false for task with null status.");
    }

    @Test
    void testCompletedTaskWithCompletedStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("Completed");
        assertTrue(TaskPredicates.COMPLETED_TASK.test(task),
                "Predicate should return true for task with 'Completed' status.");
    }

    @Test
    void testCompletedTaskWithInProgressStatus() {
        MainTaskDto task = new MainTaskDto();
        task.setStatus("In Progress");
        assertFalse(TaskPredicates.COMPLETED_TASK.test(task),
                "Predicate should return false for task with 'In Progress' status.");
    }
}
