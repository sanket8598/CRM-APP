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

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 22/08/2023
 *
 */
@Entity
@Table(name = "crm_lead_source_master")
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class LeadSourceMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "lead_source_id")
	private Integer leadSourceId;

	@Column(name = "source_name")
	private String sourceName;
	
	@OneToMany(mappedBy = "leadSourceMaster")
	private List<Leads> leads=new ArrayList<>();

}
