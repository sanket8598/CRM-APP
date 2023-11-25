package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.PERSIST;
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
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_metting_attachment")
@Where(clause = "deleted_by is null")
public class MettingAttachments extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "metting_attch_id")
	private Integer mettingAttchId;

	@Column(name = "metting_attachment")
	private String mettingAttachmentData;

	@Column(name = "metting_attach_type")
	private String mettingAttachType;

	@Column(name = "metting_attach_name")
	private String mettingAttachName;

	@ManyToOne(cascade = PERSIST)
	@JoinColumn(name = "metting_id")
	private Mettings mettings;
}
