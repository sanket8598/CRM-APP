package ai.rnt.crm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is base interface for all services it contains basic common operation of table 
 * and common methods. This interface requires two generic arguments first is entity and 
 * another is dto. 
 * @param entity
 * @param dto
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 */
@Transactional(readOnly = true)
public interface CrudService<ENTITY, DTO> {

	/**
	 * This method is used to persist the @param into table.
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param entity to save
	 * @return Convert saved entity into dto and return
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 1.0
	 */
	@Transactional
	default Optional<DTO> save(ENTITY entity) throws Exception {
		throw new UnsupportedOperationException("save method is not implemented");
	}
	/**
	 * @param entitys
	 * @return
	 * @throws Exception
	 * @since 18-08-2023
	 * @version 1.3
	 */
	@Transactional
	default List<DTO> saveAll(Collection<ENTITY> entitys) throws Exception {
		throw new UnsupportedOperationException("save all method is not implemented");
	}
	/**
	 * This method is used to edit the entity into table.
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param entity to save
	 * @return Convert saved entity into dto and return
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 1.0
	 */
	@Transactional
	default Optional<DTO> edit(ENTITY entity) throws Exception {
		throw new UnsupportedOperationException("update method is not implemented");
	}
	/**
	 * @param entitys
	 * @return
	 * @throws Exception
	 * @since 18-08-2023
	 * @version 1.3
	 */
	@Transactional
	default List<DTO> editAll(Collection<ENTITY> entitys) throws Exception {
		throw new UnsupportedOperationException("edit all method is not implemented");
	}
	/**
	 * This method is used to delete the entity from table. 
	 * This method is marked with Transactional annotation
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param id of entity to delete
	 * @return true if entity is deleted successfully
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 1.0
	 */
	@Transactional
	default boolean delete(Integer id) throws Exception {
		throw new UnsupportedOperationException("delete method is not implemented");
	}
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @since 18-08-2023
	 * @version 1.3
	 */
	@Transactional
	default boolean deleteById(Integer id) throws Exception {
		throw new UnsupportedOperationException("deleteById method is not implemented");
	}
	/**
	 * This method is used to fetch the entity form database using provided id 
	 * (primary key of the entity).
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param id of the entity to find entity record. 
	 * @return Convert entity into dto and return
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 1.0
	 */
	default Optional<DTO> getById(Integer id) throws Exception {
		throw new UnsupportedOperationException("getById method is not implemented");
	}
	/**
	 * @param id of the entity to find entity record. 
	 * @return Convert entity into dto and return
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 2.0
	 */
	default Optional<ENTITY> getEntityById(Integer id) throws Exception {
		throw new UnsupportedOperationException("getEntityById method is not implemented");
	}
	/**
	 * This method is used to fetch the entity form database using provided multiple id's
	 * (primary key of the entity).
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param ids aray of the entity to find entity record. 
	 * @return Convert entity into dto and return
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 2.0
	 */
	default Collection<DTO> getByIds(Integer... ids) throws Exception {
		throw new UnsupportedOperationException("getByIds method is not implemented");
	}
	/**
	 * This method is used to fetch the entitys form database using provided multiple id's
	 * (primary key of the entity).
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param ids aray of the entity to find entity record. 
	 * @return Convert entity into dto and return
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 2.0
	 */
	default Collection<ENTITY> getEntitysByIds(Integer... ids) throws Exception {
		throw new UnsupportedOperationException("getByIds method is not implemented");
	}
	/**
	 * This method is used to find the single entity using given JPA example.
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param example JPA Example of the entity to find the one entity
	 * @return Convert entity into dto and return
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 1.0
	 */
	default Optional<DTO> getOneByExample(Example<ENTITY> example) throws Exception {
		throw new UnsupportedOperationException("getOneByExample method is not implemented");
	}
	/**
	 * This method is used to find the multiple entity's using given JPA example.
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @param example JPA Example of the entity to find the one entity
	 * @return Collection of converted entity into dto objects
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * 
	 * @author Shubham Lekurwale
	 * @since version 1.0
	 */
	default Collection<DTO> getAllByExample(Example<ENTITY> example) throws Exception {
		throw new UnsupportedOperationException("getAllByExample method is not implemented");
	}
	/**
	 * This method is used to fetch all the entity's from table.
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException an exception
	 * @return Collection of converted entity into dto objects
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 1.0
	 */
	default Collection<DTO> getAll() throws Exception {
		throw new UnsupportedOperationException("getAll method is not implemented");
	}
	/**
	 * This method is used to check is the entity present in the table.
	 * If this method is not implemented then this method will throw
	 * UnsupportedOperationException
	 * @return true if record found
	 * @throws Exception <br>
	 * {@link UnsupportedOperationException} if this method is not implemented
	 * @since version 1.0
	 */
	default boolean existsById(Integer id) throws Exception {
		throw new UnsupportedOperationException("existByid method is not implemented");
	}

	
}
