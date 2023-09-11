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
@Table(name = "crm_service_falls_master")
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class ServiceFallsMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "service_falls_id")
	@GeneratedValue(strategy = IDENTITY)
	private Integer serviceFallsId;

	@Column(name = "service_name")
	private String serviceName;
	
	@OneToMany(mappedBy = "serviceFallsMaster")
	private List<Leads> leads=new ArrayList<>();

}
