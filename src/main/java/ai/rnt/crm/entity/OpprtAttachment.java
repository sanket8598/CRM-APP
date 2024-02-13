package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

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
 * @author Sanket Wakankar
 * @version 2.0
 * @since 13-02-2024.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_opt_attachment")
@Where(clause = "deleted_by is null")
public class OpprtAttachment extends Auditable{

	private static final long serialVersionUID = -7490002429382501320L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "opty_attach_id")
	private Integer optAttchId;

	@Column(name = "attachment")
	private String attachmentData;

	@Column(name = "attach_type")
	private String attachType;

	@Column(name = "attach_name")
	private String attachName;
	
	@Column(name = "attach_of")
	private String attachmentOf;

	@ManyToOne(cascade = ALL)
	@JoinColumn(name = "opty_id")
	private Opportunity opportunity;
}
