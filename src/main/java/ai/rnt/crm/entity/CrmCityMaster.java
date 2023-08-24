package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "crm_city_master")
public class CrmCityMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "city_id")
	private Integer cityId;

	@Column(name = "state_id")
	private Integer stateId;

	@Column(name = "city")
	private String city;

}