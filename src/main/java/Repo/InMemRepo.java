package Repo;

import Domain.BaseEntity;
import sun.security.validator.ValidatorException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemRepo<ID, T extends BaseEntity<ID>> implements RepositoryInterface<ID, T> {

    private Map<ID, T> entities;

    public InMemRepo(Map<ID, T> entities) {
        this.entities = entities;
    }

    @Override
    public Optional<T> findOne(ID id) {
        if(id == null){
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
        if(entitiy == null){
            throw new IllegalArgumentException("ID must not be null!");
        }
        return Optional.ofNullable(entities.putIfAbsent(entitiy.getId(), entitiy));
    }
}
