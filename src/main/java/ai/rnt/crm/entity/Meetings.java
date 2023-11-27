package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

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

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_meeting")
@Where(clause = "deleted_by is null")
public class Meetings extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mtng_id")
	private Integer meetingId;

	@Column(name = "mtng_title")
	private String meetingTitle;

	@Column(name = "participates")
	private String participates;

	@Column(name = "mtng_start_date")
	private Date startDate;

	@Column(name = "mtng_end_date")
	private Date endDate;

	@Column(name = "mtng_start_time")
	private String startTime;

	@Column(name = "mtng_end_time")
	private String endTime;

	@Column(name = "all_day")
	private boolean allDay;

	@Column(name = "mtng_location")
	private String location;

	@Column(name = "mtng_description")
	private String description;

	@Column(name = "mtng_mode")
	private String mettingMode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_id")
	private Leads lead;

	@OneToMany(mappedBy = "meetings", cascade = { REMOVE, REFRESH }, orphanRemoval = true)
	private List<MeetingAttachments> meetingAttachments = new ArrayList<>();
	
	@OneToMany(mappedBy = "meetings", cascade = ALL, orphanRemoval = true)
	private List<MeetingTask> meetingTasks = new ArrayList<>();

}
