package ai.rnt.crm.dao.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.VisitDto;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;

public interface VisitDaoService extends CrudService<Visit, VisitDto> {

	Visit saveVisit(Visit visit);

	List<Visit> getVisitsByLeadId(Integer leadId);

	Optional<Visit> getVisitsByVisitId(Integer visitId);

	VisitTask addVisitTask(VisitTask visitTask);

	Optional<VisitTask> getVisitTaskById(Integer taskId);

	List<VisitTask> getAllTask();

	List<VisitTask> getTodaysAllVisitTask(LocalDate todayAsDate, String time);

	List<Visit> getVisitsByLeadIdAndIsOpportunity(Integer leadId);

	List<Visit> getAllLeadVisits(boolean isOpportunity);
}
