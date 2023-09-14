package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.entity.Visit;

public interface VisitDaoService extends CrudService<Visit, VisitDto> {

	Visit saveVisit(Visit visit);

}
