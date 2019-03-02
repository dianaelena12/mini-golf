package Repo;

import Domain.BaseEntity;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;

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
    public Optional<T> save(T entitiy) throws ValidatorException {
        if (entitiy == null) {
            throw new IllegalArgumentException("ID must not be null!");
        }
        validator.validate(entitiy);
        return Optional.ofNullable(entities.putIfAbsent(entitiy.getId(), entitiy));
    }
}
