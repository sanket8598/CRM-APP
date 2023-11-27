package ai.rnt.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Meetings;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 25/11/2023.
 *
 */
public interface MettingRepository extends JpaRepository<Meetings, Integer> {

}
