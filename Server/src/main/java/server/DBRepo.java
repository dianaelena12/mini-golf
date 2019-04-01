package server;


import common.Domain.BaseEntity;
import common.Domain.Validators.Validator;
import common.Domain.Validators.ValidatorException;
import server.Paging.Impl.PageImpl;
import server.Paging.Page;
import server.Paging.Pageable;
import server.Paging.PagingRepository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class DBRepo<ID extends Serializable, T extends BaseEntity<ID>> implements PagingRepository<ID, T> {
    private Validator<T> validator;
    private static final String URL = "jdbc:postgresql://localhost:5432/MPPLab";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "alex";

    public DBRepo(Validator<T> validator) {
        this.validator = validator;
    }

    public DBRepo() {
    }

    public abstract Optional<T> saveInDB(T entity);

    public abstract Optional<T> deleteFromDB(ID id);

    public abstract Optional<T> updateInDB(T entity);

    public abstract Optional<T> getFromDB(ID id);


    public abstract Set<T> findAllFromDB();

    public Connection connectToDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;

    }

    @Override
    public Optional<T> findOne(ID id) {
        return this.getFromDB(id);
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        validator.validate(entity);
        return this.saveInDB(entity);
    }

    @Override
    public Optional<T> delete(ID id) {
        return this.deleteFromDB(id);
    }

    @Override
    public Iterable<T> findAll() {
        return this.findAllFromDB();
    }

    @Override
    public Optional update(T entity) throws ValidatorException {
        validator.validate(entity);
        return this.updateInDB(entity);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Set<T> entities = findAllFromDB();
        AtomicInteger counter = new AtomicInteger(0);
        List<T> list = (new ArrayList<>(entities.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / pageable.getPageSize())).values()))
                .get(pageable.getPageNumber());
        return new PageImpl<>(pageable, list.stream());
    }
}
