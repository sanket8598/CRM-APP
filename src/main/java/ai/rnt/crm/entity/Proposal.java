package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @since 16/04/2024
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_opty_proposal")
@Where(clause = "deleted_by is null")
public class Proposal extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "prop_id")
	private Integer propId;

	@Column(name = "gen_prop_id")
	private String genPropId;

	@Column(name = "owner_name")
	private String ownerName;

	@Column(name = "currency")
	private String currency;

	@Column(name = "prop_description")
	private String propDescription;

	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "opty_id")
	private Opportunity opportunity;

	@OneToMany(mappedBy = "proposal", cascade = ALL, orphanRemoval = true)
	private List<ProposalServices> proposalServices = new ArrayList<>();
}
