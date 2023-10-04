package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.entity.Visit;

public interface VisitDaoService extends CrudService<Visit, VisitDto> {

	Visit saveVisit(Visit visit);

	List<Visit> getVisitsByLeadId(Integer leadId);

	Optional <Visit> getVisitsByVisitId(Integer visitId);

}
