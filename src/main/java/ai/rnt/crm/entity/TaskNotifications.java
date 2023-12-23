package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "crm_task_notifications")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class TaskNotifications extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "notif_id")
	private Integer notifId;

	@JoinColumn(name = "call_task_id", updatable = true)
	@ManyToOne
	private PhoneCallTask callTask;

	@JoinColumn(name = "visit_task_id", updatable = true)
	@ManyToOne
	private VisitTask visitTask;

	@JoinColumn(name = "mtg_task_id", updatable = true)
	@ManyToOne
	private MeetingTask meetingTask;
	
	@JoinColumn(name = "notif_to", updatable = true)
	@ManyToOne
	private EmployeeMaster notifTo;

	@Column(name = "notif_msg")
	private String notifMessage;

	@Column(name = "notif_status")
	private boolean notifStatus;
}
