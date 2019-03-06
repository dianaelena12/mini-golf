package Repo;

import Domain.BaseEntity;
import Domain.Validators.ValidatorException;

import java.util.Optional;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 */

public interface RepositoryInterface<ID, T extends BaseEntity<ID>> {
    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */

    Optional<T> findOne(ID id);


    /**
     * @return all entries
     */

    Iterable<T> findAll();


    Optional<T> save(T entity) throws ValidatorException;
    /**
     * Saves the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */

    Optional<T> delete(ID id);


    Optional<T> update(T entity) throws ValidatorException;
}

