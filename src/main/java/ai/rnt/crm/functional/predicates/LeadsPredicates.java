package ai.rnt.crm.functional.predicates;

import static ai.rnt.crm.constants.StatusConstants.CANCELED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_DISQUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.COMPLETE;
import static ai.rnt.crm.constants.StatusConstants.FOLLOW_UP;
import static ai.rnt.crm.constants.StatusConstants.LOST;
import static ai.rnt.crm.constants.StatusConstants.NON_CONTACTABLE;
import static ai.rnt.crm.constants.StatusConstants.NON_WORKABLE;
import static ai.rnt.crm.constants.StatusConstants.NO_LONGER_INTERESTED;
import static ai.rnt.crm.constants.StatusConstants.OPEN;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.constants.StatusConstants.SEND;
import static ai.rnt.crm.functional.predicates.UpnextPredicate.UPNEXT;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Visit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LeadsPredicates {

	public static final Predicate<Visit> ACTIVE_VISIT = visit -> (isNull(visit.getStatus())
			|| visit.getStatus().equalsIgnoreCase(SAVE));
	public static final Predicate<Meetings> ACTIVE_MEETING = meeting -> (isNull(meeting.getMeetingStatus())
			|| meeting.getMeetingStatus().equalsIgnoreCase(SAVE));
	public static final Predicate<Call> ACTIVE_CALL = call -> (isNull(call.getStatus())
			|| call.getStatus().equalsIgnoreCase(SAVE));

	/*
	 * * This Predicate return true if it the lead has disqualified.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Leads> DISQUALIFIED_LEAD_FILTER = l -> nonNull(l.getStatus())
			&& l.getStatus().equalsIgnoreCase(CLOSE_AS_DISQUALIFIED)
			&& (l.getDisqualifyAs().equalsIgnoreCase(LOST) || l.getDisqualifyAs().equalsIgnoreCase(NON_CONTACTABLE)
					|| l.getDisqualifyAs().equalsIgnoreCase(NO_LONGER_INTERESTED)
					|| l.getDisqualifyAs().equalsIgnoreCase(CANCELED)
					|| l.getDisqualifyAs().equalsIgnoreCase(NON_WORKABLE));
	/*
	 * * This Predicate return true if it the lead has Qaulified.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Leads> QUALIFIED_LEAD_FILTER = l -> nonNull(l.getStatus())
			&& l.getStatus().equalsIgnoreCase(CLOSE_AS_QUALIFIED)
			&& (l.getDisqualifyAs().equalsIgnoreCase(QUALIFIED) || l.getDisqualifyAs().equalsIgnoreCase(FOLLOW_UP));
	/*
	 * * This Predicate return true if it the lead has Open.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Leads> OPEN_LEAD_FILTER = l -> nonNull(l.getStatus())
			&& l.getStatus().equalsIgnoreCase(OPEN);
	/*
	 * * This Predicate return true if it the lead has Open.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Leads> CLOSE_LEAD_FILTER = l -> nonNull(l.getStatus())
			&& !l.getStatus().equalsIgnoreCase(OPEN);
	/*
	 * * This BiPredicate return true if it the lead has assigned to the give
	 * userId.
	 * 
	 * @since version 1.0
	 */
	public static final BiPredicate<Leads, Integer> ASSIGNED_TO_FILTER = (l, loggedInStaffId) -> l.getEmployee()
			.getStaffId().equals(loggedInStaffId) || l.getCreatedBy().equals(loggedInStaffId);

	/*
	 * * This Predicate return true if it the call is completed for the Timeline.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Call> TIMELINE_CALL = call -> nonNull(call.getStatus())
			&& call.getStatus().equalsIgnoreCase(COMPLETE);
	/*
	 * * This Predicate return true if it the call for the Activity.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Call> ACTIVITY_CALL = call -> ACTIVE_CALL.test(call)
			&& !UPNEXT.test(convertDateDateWithTime(call.getStartDate(), call.getStartTime12Hours()));
	/*
	 * * This Predicate return true if it the email is completed for the Timeline.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Email> TIMELINE_EMAIL = email -> nonNull(email.getStatus())
			&& email.getStatus().equalsIgnoreCase(SEND);
	/*
	 * * This Predicate return true if it the email is for the Activity.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Email> ACTIVITY_EMAIL = email -> isNull(email.getStatus())
			|| email.getStatus().equalsIgnoreCase(SAVE);

	/*
	 * * This Predicate return true if it the email is for the Activity.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Visit> ACTIVITY_VISIT = visit -> ACTIVE_VISIT.test(visit)
			&& !UPNEXT.test(convertDateDateWithTime(visit.getStartDate(), visit.getStartTime12Hours()));

	/*
	 * * This Predicate return true if it the email is for the TimeLine.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Visit> TIMELINE_VISIT = visit -> nonNull(visit.getStatus())
			&& visit.getStatus().equalsIgnoreCase(COMPLETE);
	/*
	 * * This Predicate return true if it the meeting for the TimeLine.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Meetings> TIMELINE_MEETING = meeting -> nonNull(meeting.getMeetingStatus())
			&& meeting.getMeetingStatus().equalsIgnoreCase(COMPLETE);

	/*
	 * * This Predicate return true if it the meeting is for the Activity.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Meetings> ACTIVITY_MEETING = meeting -> ACTIVE_MEETING.test(meeting)
			&& !UPNEXT.test(convertDateDateWithTime(meeting.getStartDate(), meeting.getStartTime12Hours()));

	/*
	 * * This Predicate return true if it the call is for the upNext.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Call> UPNEXT_CALL = call -> ACTIVE_CALL.test(call)
			&& UPNEXT.test(convertDateDateWithTime(call.getStartDate(), call.getStartTime12Hours()));
	/*
	 * * This Predicate return true if it the visit is for the upNext.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Visit> UPNEXT_VISIT = visit -> ACTIVE_VISIT.test(visit)
			&& UPNEXT.test(convertDateDateWithTime(visit.getStartDate(), visit.getStartTime12Hours()));
	/*
	 * * This Predicate return true if it the meeting is for the upNext.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Meetings> UPNEXT_MEETING = meeting -> ACTIVE_MEETING.test(meeting)
			&& UPNEXT.test(convertDateDateWithTime(meeting.getStartDate(), meeting.getStartTime12Hours()));

}
