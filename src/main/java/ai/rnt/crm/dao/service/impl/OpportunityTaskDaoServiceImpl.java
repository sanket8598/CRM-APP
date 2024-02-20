package ai.rnt.crm.dao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.OpportunityTaskDaoService;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.repository.OpportunityTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @since 19-02-2024
 * @version 1.2
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OpportunityTaskDaoServiceImpl implements OpportunityTaskDaoService {

	private final OpportunityTaskRepository opportunityTaskRespoitory;

	@Override
	public List<OpportunityTask> getAllTask() {
		log.info("inside the OpportunityTaskDaoServiceImpl getAllTask method...");
		return opportunityTaskRespoitory.findAll();
	}

	@Override
	public OpportunityTask addOptyTask(OpportunityTask opportunityTask) {
		log.info("inside the OpportunityTaskDaoServiceImpl addOptyTask method...");
		return opportunityTaskRespoitory.save(opportunityTask);
	}
}
