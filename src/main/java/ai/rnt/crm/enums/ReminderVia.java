package ai.rnt.crm.enums;

import ai.rnt.crm.constants.StatusConstants;

public enum ReminderVia {

	EMAIL(StatusConstants.EMAIL), NOTIFICATION(StatusConstants.NOTIFICATION), BOTH(StatusConstants.BOTH),
	NULL(StatusConstants.NULL);

	private final String reminderVia;

	ReminderVia(String reminderVia) {
		this.reminderVia = reminderVia;
	}

	public String getReminderVia() {
		return reminderVia;
	}

}
