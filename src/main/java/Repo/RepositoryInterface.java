package Repo;

import Domain.BaseEntity;
import sun.security.validator.ValidatorException;

import java.util.Optional;

public interface RepositoryInterface<ID, T extends BaseEntity<ID>> {

    Optional<T> findOne(ID id);
    Iterable<T> findAll();
    Optional<T> save(T entitiy) throws ValidatorException;
}

