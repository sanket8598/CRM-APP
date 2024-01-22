package ai.rnt.crm.enums;

import ai.rnt.crm.constants.StatusConstants;

public enum TaskPriority {
	
	LOW(StatusConstants.LOW), MEDIUM(StatusConstants.MEDIUM), HIGH(StatusConstants.HIGH);
	
    private final String priority;
	
    TaskPriority(String priority) {
        this.priority = priority;
    }
    public String getPriority() {
        return priority;
    }

}
