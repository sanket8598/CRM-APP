package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.entity.ServiceFallsMaster;

public interface ServiceFallsDaoSevice {

	Optional<ServiceFallsMaster> getById(Integer serviceFallsId);

	List<ServiceFallsMaster> getAllSerciveFalls();

}