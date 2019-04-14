package springjpa.Repo;

import springjpa.Domain.BaseEntity;
import springjpa.Domain.Validators.NoEntityStored;
import springjpa.Domain.Validators.Validator;
import springjpa.Domain.Validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemRepo<ID, T extends BaseEntity<ID>> implements RepositoryInterface<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemRepo(Validator<T> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        Set<T> allEnTITIES = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEnTITIES;
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        if(entities.isEmpty()){
            throw new NoEntityStored("There are no entities in the database!");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        if(entities.isEmpty()){
            throw new NoEntityStored("There are no students in the database!");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
