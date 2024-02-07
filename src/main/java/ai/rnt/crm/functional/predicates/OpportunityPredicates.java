package ai.rnt.crm.functional.predicates;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class OpportunityPredicates {

//	/*
//	 * * This Predicate return true if it the opportunity has In-pipeline.
//	 * 
//	 * @since version 1.0
//	 */
//	public static final Predicate<Opportunity> IN_PIPELINE_OPPORTUNITIES = l -> nonNull(l.getStatus())
//			&& l.getStatus().equalsIgnoreCase(QUALIFY)
//			|| (l.getStatus().equalsIgnoreCase(ANALYSIS) || l.getStatus().equalsIgnoreCase(PROPOSE)
//					|| l.getStatus().equalsIgnoreCase(CLOSE));
//	/*
//	 * * This Predicate return true if it the opportunity has Won.
//	 * 
//	 * @since version 1.0
//	 */
//	public static final Predicate<Opportunity> WON_OPPORTUNITIES = l -> nonNull(l.getStatus())
//			&& l.getStatus().equalsIgnoreCase(WON);
//	/*
//	 * * This Predicate return true if it the opportunity has Loss.
//	 * 
//	 * @since version 1.0
//	 */
//	public static final Predicate<Opportunity> LOSS_OPPORTUNITIES = l -> nonNull(l.getStatus())
//			&& l.getStatus().equalsIgnoreCase(LOST);
//
//	public static final BiPredicate<Opportunity, Integer> ASSIGNED_OPPORTUNITIES = (l,
//			loggedInStaffId) -> l.getEmployee().getStaffId().equals(loggedInStaffId)
//					|| l.getCreatedBy().equals(loggedInStaffId);
}
