package ai.rnt.crm.entity;

import static ai.rnt.crm.util.AuditAwareUtil.activeProfile;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.util.Objects.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import ai.rnt.crm.security.UserDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * This Abstract Class used to give the fields which we used in every entity.
 * @author Sanket Wakankar
 * @since 19-08-2023
 * @version 1.0
 */
// @formatter:off
 
@Embeddable
@MappedSuperclass
@Getter
@Setter
public abstract class Auditable implements Serializable{
	

	private static final long serialVersionUID = -406869010295120058L;

	@Column(name = "created_by", nullable = false, updatable = false)
	private Integer createdBy;

	@Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
	private LocalDateTime createdDate;
	
	@Column(name = "updated_by")
	private Integer updatedBy;

	@Column(name = "updated_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDate;

	@Column(name = "deleted_by")
	protected Integer deletedBy;

	@Column(name = "deleted_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime deletedDate;
	
	@PrePersist
	public void beforPersist() {
		if(nonNull(getContext()) && nonNull(getContext().getAuthentication()) 
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details =(UserDetail) getContext().getAuthentication().getDetails();
			this.createdBy = details.getStaffId();
		}
		 this.createdDate = now();
			
	}
	
	@PreUpdate
	public void beforUpdate() {
		if(nonNull(getContext()) && nonNull(getContext().getAuthentication()) 
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details =(UserDetail) getContext().getAuthentication().getDetails();
			this.updatedBy = details.getStaffId();
		}
		this.updatedDate = now();
	}

	@PreRemove
	public void beforDelete() {
		if(nonNull(getContext()) && nonNull(getContext().getAuthentication()) 
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details =(UserDetail) getContext().getAuthentication().getDetails();
			this.deletedBy = details.getStaffId();
		}
		this.deletedDate = now();
	}
}
//@formatter:on