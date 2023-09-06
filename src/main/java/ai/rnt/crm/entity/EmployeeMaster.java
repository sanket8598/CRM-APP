package ai.rnt.crm.entity;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sanket Wakankar
 * @since 19-08-2023
 * @version 1.0
 */
// @formatter:off
 
@Entity
@Table(name = "employee_master")
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted_by is null")
public class EmployeeMaster extends Auditable {

	private static final long serialVersionUID = 931377982617396405L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Staff_ID")
	private Integer staffId;

	@Column(name = "Password")
	private String password;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "F_Name")
	private String firstName; // first_name

	@Column(name = "M_Name")
	private String middleName; // middle_name

	@Column(name = "L_Name")
	private String lastName;

	@Column(name = "Email_Id")
	private String emailId;

	@Column(name = "Manager_ID")
	private Integer managerId;

	@Column(name = "Emp_Job_Title")
	private String employeeJobTitle;


	@Column(name = "date_of_departure")
	private LocalDate departureDate;

	@ManyToMany(fetch = EAGER, cascade = { PERSIST, MERGE, DETACH, REFRESH })
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
	               inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Where(clause = "deleted_by is null")
	private List<RoleMaster> employeeRole = new ArrayList<>();
	
	@OneToMany(mappedBy = "assignTo",cascade =ALL)
	private List<Leads> leads = new ArrayList<>();

}
//@formatter:on