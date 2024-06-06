package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;

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
 * @since 06/06/2024
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_description")
@Where(clause = "deleted_by is null")
public class Description extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "description_id")
	private Integer descId;

	@Column(name = "subject")
	private String subject;

	@Column(name = "status")
	private String status;

	@Column(name = "description")
	private String desc;

	@Column(name = "action")
	private String action;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "is_opportunity", columnDefinition = "default false")
	private Boolean isOpportunity;

	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "lead_id")
	private Leads lead;
}
