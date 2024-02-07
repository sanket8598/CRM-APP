package ai.rnt.crm.dao;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.ServiceFallsDto;
import ai.rnt.crm.entity.ServiceFallsMaster;

public interface ServiceFallsDaoSevice extends CrudService<ServiceFallsMaster, ServiceFallsDto>{

	Optional<ServiceFallsMaster> getServiceFallById(Integer serviceFallsId);

	List<ServiceFallsMaster> getAllSerciveFalls();

	Optional <ServiceFallsMaster> findByName(String serviceFalls);

}