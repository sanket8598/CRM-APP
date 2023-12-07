package ai.rnt.crm.functional.predicates;

import static ai.rnt.crm.constants.StatusConstants.CANCELED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_DISQUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.LOST;
import static ai.rnt.crm.constants.StatusConstants.NON_CONTACTABLE;
import static ai.rnt.crm.constants.StatusConstants.NON_WORKABLE;
import static ai.rnt.crm.constants.StatusConstants.NO_LONGER_INTERESTED;
import static ai.rnt.crm.constants.StatusConstants.OPEN;
import static ai.rnt.crm.constants.StatusConstants.FOLLOW_UP;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED;
import static java.util.Objects.nonNull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import ai.rnt.crm.entity.Leads;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class LeadsPredicates {

	
	/*
	 * * This Predicate return true if it the lead has disqualified.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Leads> DISQUALIFIED_LEAD_FILTER = l ->nonNull(l.getStatus()) && l.getStatus().equalsIgnoreCase(CLOSE_AS_DISQUALIFIED)
		&& (l.getDisqualifyAs().equalsIgnoreCase(LOST)
				|| l.getDisqualifyAs().equalsIgnoreCase(NON_CONTACTABLE)
				|| l.getDisqualifyAs().equalsIgnoreCase(NO_LONGER_INTERESTED)
				|| l.getDisqualifyAs().equalsIgnoreCase(CANCELED)
		        || l.getDisqualifyAs().equalsIgnoreCase(NON_WORKABLE));
	/*
	 * * This Predicate return true if it the lead has Qaulified.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Leads> QUALIFIED_LEAD_FILTER = l ->nonNull(l.getStatus()) && l.getStatus().equalsIgnoreCase(CLOSE_AS_QUALIFIED)
			&& (l.getDisqualifyAs().equalsIgnoreCase(QUALIFIED)
					|| l.getDisqualifyAs().equalsIgnoreCase(FOLLOW_UP));
	/*
	 * * This Predicate return true if it the lead has Open.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Leads> OPEN_LEAD_FILTER = l ->nonNull(l.getStatus()) && l.getStatus().equalsIgnoreCase(OPEN);
	/*
	 * * This BiPredicate return true if it the lead has assigned to the give userId.
	 * 
	 * @since version 1.0
	 */
	public static final BiPredicate<Leads,Integer> ASSIGNED_TO_FILTER = (l,loggedInStaffId) ->l.getEmployee().getStaffId().equals(loggedInStaffId);
	
}
