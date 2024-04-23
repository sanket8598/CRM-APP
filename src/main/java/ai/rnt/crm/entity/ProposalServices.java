package ai.rnt.crm.entity;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_proposal_services")
@Where(clause = "deleted_by is null")
public class ProposalServices extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "prop_servcs_id")
	private Integer propServiceId;

	@Column(name = "price")
	private String servicePrice;

	@ManyToOne
	@JoinColumn(name = "prop_id")
	private Proposal proposal;

	@ManyToOne
	@JoinColumn(name = "service_falls_id")
	private ServiceFallsMaster serviceFallsMaster;

}
