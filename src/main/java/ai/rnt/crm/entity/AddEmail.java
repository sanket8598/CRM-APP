package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
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
 * @version 1.0
 * @since 23/08/2023.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_add_email")
@Where(clause = "deleted_by is null")
public class AddEmail extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "add_mail_id")
	private Integer addMailId;

	@Column(name = "mail_from")
	private String mailFrom;

	@Column(name = "mail_to")
	private String toMail;

	@Column(name = "cc_mail")
	private String ccMail;

	@Column(name = "bcc_mail")
	private String bccMail;

	@Column(name = "subject")
	private String subject;

	@Column(name = "content")
	private String content;
	
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="lead_id")
	private Leads lead;
	
	@OneToMany(mappedBy = "mail",cascade = {REMOVE,REFRESH},orphanRemoval = true)
	private List<Attachment> attachment = new ArrayList<>();

}
