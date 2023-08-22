package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer>{

}
