package server.Repository;

import common.Domain.Validators.ValidatorException;
import server.Paging.Page;
import server.Paging.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 */

public interface RepositoryInterface<T> {
    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */

    Optional<T> findOne(long id);


    /**
     * @return all entries
     */

    List<T> findAll();

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    void save(T entity) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    void delete(long id);

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    void update(T entity) throws ValidatorException;


    Page<T> findAll(Pageable pageable);

}

