package ai.rnt.crm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 24/11/2023.
 * @version 1.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_excel_header_master")
@Where(clause = "deleted_by is null")
public class ExcelHeaderMaster extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "header_id")
	private Integer headerId;

	@Column(name = "header_name")
	private String headerName;

}
