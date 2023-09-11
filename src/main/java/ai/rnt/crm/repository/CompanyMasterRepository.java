package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.CompanyMaster;

public interface CompanyMasterRepository extends JpaRepository<CompanyMaster,Integer>{

	CompanyMaster findByCompanyName(String companyName);

}
