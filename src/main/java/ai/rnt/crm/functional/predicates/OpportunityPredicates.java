package ai.rnt.crm.functional.predicates;

import static ai.rnt.crm.constants.OppurtunityStatus.ANALYSIS;
import static ai.rnt.crm.constants.OppurtunityStatus.CLOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.LOST;
import static ai.rnt.crm.constants.OppurtunityStatus.PROPOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.QUALIFY;
import static ai.rnt.crm.constants.OppurtunityStatus.WON;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import ai.rnt.crm.entity.Opportunity;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class OpportunityPredicates {

	/*
	 * * This Predicate return true if it the opportunity has In-pipeline.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Opportunity> IN_PIPELINE_OPPORTUNITIES = l -> nonNull(l.getStatus())
			&& (l.getStatus().equalsIgnoreCase(QUALIFY)
			|| (l.getStatus().equalsIgnoreCase(ANALYSIS) || l.getStatus().equalsIgnoreCase(PROPOSE)
					|| l.getStatus().equalsIgnoreCase(CLOSE)));
	/*
	 * * This Predicate return true if it the opportunity has Won.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Opportunity> WON_OPPORTUNITIES = l -> nonNull(l.getStatus())
			&& l.getStatus().equalsIgnoreCase(WON);
	/*
	 * * This Predicate return true if it the opportunity has Loss.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<Opportunity> LOSS_OPPORTUNITIES = l -> nonNull(l.getStatus())
			&& l.getStatus().equalsIgnoreCase(LOST);

	public static final BiPredicate<Opportunity, Integer> ASSIGNED_OPPORTUNITIES = (l,
			loggedInStaffId) -> l.getEmployee().getStaffId().equals(loggedInStaffId)
					|| l.getCreatedBy().equals(loggedInStaffId);
}
