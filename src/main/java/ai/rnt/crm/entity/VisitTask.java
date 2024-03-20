package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "crm_visit_task")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class VisitTask extends Task {

	private static final long serialVersionUID = 5725258082414270874L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "visit_task_id")
	private Integer visitTaskId;

	@JoinColumn(name = "task_assign_to", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private EmployeeMaster assignTo;

	@JoinColumn(name = "visit_id", updatable = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne
	private Visit visit;

	@OneToMany(mappedBy = "visitTask", cascade = ALL)
	private List<TaskNotifications> taskNotifications;
	
}
