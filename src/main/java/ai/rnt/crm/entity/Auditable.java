package ai.rnt.crm.entity;

import static java.time.LocalDateTime.now;
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

/**
 * @author Sanket Wakankar
 * @since 19-08-2023
 * @version 1.0
 */
// @formatter:off
 
@Embeddable
@MappedSuperclass
public abstract class Auditable implements Serializable{
	

	private static final long serialVersionUID = -406869010295120058L;

	@Column(name = "created_by", nullable = false, updatable = false)
	private String createdBy;

	@Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
	private LocalDateTime createdDate;
	
	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDate;

	@Column(name = "deleted_by")
	protected String deletedBy;

	@Column(name = "deleted_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime deletedDate;
	
	@PrePersist
	public void beforPersist() {
		if(nonNull(getContext()) && nonNull(getContext().getAuthentication()) 
				&& nonNull(getContext().getAuthentication().getName())) {
			this.createdBy = getContext().getAuthentication().getName();
		}
		this.createdDate = now();
	}
	
	@PreUpdate
	public void beforUpdate() {
		if(nonNull(getContext()) && nonNull(getContext().getAuthentication()) 
				&& nonNull(getContext().getAuthentication().getName())) {
			this.updatedBy = getContext().getAuthentication().getName();
		}
		this.updatedDate = now();
	}

	@PreRemove
	public void beforDelete() {
		if(nonNull(getContext()) && nonNull(getContext().getAuthentication()) 
				&& nonNull(getContext().getAuthentication().getName())) {
			this.deletedBy = getContext().getAuthentication().getName();
		}
		this.deletedDate = now();
	}
}
//@formatter:on