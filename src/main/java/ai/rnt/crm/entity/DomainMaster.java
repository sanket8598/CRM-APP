package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "domain_master")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class DomainMaster extends Auditable{

	private static final long serialVersionUID = -2789944798428896560L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="domain_id")
	private Integer domainId;
	
	@Column(name="domain_name")
	private String domainName;
	
	@OneToMany(mappedBy = "domainMaster")
	private List<Leads> leads=new ArrayList<>();
}
