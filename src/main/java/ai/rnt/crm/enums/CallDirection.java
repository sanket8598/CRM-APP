package ai.rnt.crm.enums;

import ai.rnt.crm.constants.StatusConstants;

public enum CallDirection {
	
	INCOMING(StatusConstants.INCOMING), OUTGOING(StatusConstants.OUTGOING);
	
    private final String direction;
	
    CallDirection(String direction) {
        this.direction = direction;
    }
    public String getDirection() {
        return direction;
    }
}
