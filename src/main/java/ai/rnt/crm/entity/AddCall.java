package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 23/08/2023
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_add_call")
@Where(clause = "deleted_by is null")
public class AddCall extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "add_call_id")
	private Integer addCallId;

	@Column(name = "call_from")
	private String callFrom;

	@Column(name = "call_to")
	private String callTo;

	@Column(name = "subject")
	private String subject;

	@Column(name = "direction")
	private String direction;

	@Column(name = "phone_no")
	private Integer phoneNo;

	@Column(name = "comment")
	private String comment;

	@Column(name = "duration")
	private String duration;
	
	@Column(name = "due_date")
	private Date dueDate;

	@ManyToOne
	@JoinColumn(name="lead_id")
	private Leads lead;

}
