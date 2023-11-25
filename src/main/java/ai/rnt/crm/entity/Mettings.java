package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
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

import ai.rnt.crm.enums.MettingMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crm_metting")
@Where(clause = "deleted_by is null")
public class Mettings extends Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "metting_id")
	private Integer mettingId;

	@Column(name = "metting_title")
	private String mettingTitle;

	@Column(name = "participates")
	private String participates;

	@Column(name = "metting_start_date")
	private Date startDate;

	@Column(name = "metting_end_date")
	private Date endDate;

	@Column(name = "metting_start_time")
	private String startTime;

	@Column(name = "metting_end_time")
	private String endTime;

	@Column(name = "all_day")
	private boolean allDay;

	@Column(name = "metting_location")
	private String location;

	@Column(name = "metting_description")
	private String description;

	@Column(name = "metting_mode")
	private String mettingMode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_id")
	private Leads lead;

	@OneToMany(mappedBy = "mettings", cascade = { REMOVE, REFRESH }, orphanRemoval = true)
	private List<MettingAttachments> mettingAttachments = new ArrayList<>();

}
