package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.FetchMode.JOIN;

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

import org.hibernate.annotations.Fetch;
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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_state_master")
@Where(clause = "deleted_by is null")
public class StateMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "state_id")
	private Integer stateId;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryMaster country;

	@Column(name = "state")
	private String state;

	@OneToMany(cascade = ALL, mappedBy = "state")
	private List<CompanyMaster> contacts = new ArrayList<>();

	@OneToMany(cascade = ALL, mappedBy = "state")
	@Fetch(JOIN)
	private List<CityMaster> cities = new ArrayList<>();

}
