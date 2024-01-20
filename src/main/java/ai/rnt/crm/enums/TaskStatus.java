package ai.rnt.crm.enums;

import static ai.rnt.crm.constants.StatusConstants.TASK_COMPLETED;
import static ai.rnt.crm.constants.StatusConstants.TASK_IN_PROGRESS;
import static ai.rnt.crm.constants.StatusConstants.TASK_NOT_STARTED;
import static ai.rnt.crm.constants.StatusConstants.TASK_ON_HOLD;

public enum TaskStatus {

	NOT_STARTED(TASK_NOT_STARTED), COMPLETED(TASK_COMPLETED), IN_PROGRESS(TASK_IN_PROGRESS), ON_HOLD(TASK_ON_HOLD);
    private final String status;
	
    TaskStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
