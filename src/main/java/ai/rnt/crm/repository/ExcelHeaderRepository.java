package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.ExcelHeaderMaster;

/**
 * @author Nikhil Gaikwad
 *@since 24/11/2023.
 *@version 1.0
 */
public interface ExcelHeaderRepository extends JpaRepository<ExcelHeaderMaster, Integer> {

}
