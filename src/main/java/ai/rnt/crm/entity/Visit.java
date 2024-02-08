package ai.rnt.crm.entity;

import static ai.rnt.crm.constants.DateFormatterConstant.TIME_12_HRS;
import static ai.rnt.crm.constants.DateFormatterConstant.TIME_24_HRS;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 14-09-2023.
 * @version 1.0
 */

@Entity
@Table(name = "crm_visit")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class Visit extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "visit_id")
	private Integer visitId;

	@Column(name = "location")
	private String location;

	@Column(name = "subject")
	private String subject;

	@Column(name = "content")
	private String content;

	@Column(name = "comment")
	private String comment;

	@Column(name = "duration")
	private String duration;

	@Column(name = "start_date", columnDefinition = "date")
	private Date startDate;

	@Column(name = "end_date", columnDefinition = "date")
	private Date endDate;

	@Column(name = "start_time")
	private String startTime;

	@Column(name = "end_time")
	private String endTime;

	@Column(name = "all_day")
	private boolean allDay;

	@Column(name = "status")
	private String status;

	@Column(name = "participants")
	private String participates;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_by", updatable = true, nullable = false)
	private EmployeeMaster visitBy;

	@ManyToOne
	@JoinColumn(name = "lead_id")
	private Leads lead;
	
	@ManyToOne
	@JoinColumn(name = "opty_id")
	private Opportunity opportunity;

	@OneToMany(mappedBy = "visit", cascade = ALL, orphanRemoval = true)
	private List<VisitTask> visitTasks = new ArrayList<>();

	@Transient
	public String getStartTime12Hours() {
		if (nonNull(getStartTime())) {
			try {
				return TIME_12_HRS.format(TIME_24_HRS.parse(getStartTime()));
			} catch (ParseException e) {
				return getStartTime();
			}
		}
		return null;
	}

	@Transient
	public String getEndTime12Hours() {
		if (nonNull(getEndTime()))
			try {
				return TIME_12_HRS.format(TIME_24_HRS.parse(getEndTime()));
			} catch (ParseException e) {
				return getEndTime();
			}
		return null;
	}

}
