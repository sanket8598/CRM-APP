package ai.rnt.crm.enums;

import static ai.rnt.crm.constants.StatusConstants.CANCELED;
import static ai.rnt.crm.constants.StatusConstants.NON_CONTACTABLE;
import static ai.rnt.crm.constants.StatusConstants.NON_WORKABLE;
import static ai.rnt.crm.constants.StatusConstants.NO_LONGER_INTERESTED;

import ai.rnt.crm.constants.StatusConstants;

public enum DisqualifiedAs {
	
 LOST(StatusConstants.LOST),NO_CONTACT(NON_CONTACTABLE),CANCELLED(CANCELED), NO_INTEREST(NO_LONGER_INTERESTED), NOT_WORKABLE(NON_WORKABLE);
	
    private final String disqualifiedAs;
	
    DisqualifiedAs(String disqualifiedAs) {
        this.disqualifiedAs = disqualifiedAs;
    }
    public String getDisqualifiedAs() {
        return disqualifiedAs;
    }
}
