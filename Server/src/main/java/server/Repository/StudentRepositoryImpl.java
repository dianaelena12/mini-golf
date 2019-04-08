package server.Repository;

import common.Domain.Student;
import common.Domain.Validators.Validator;
import common.Domain.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import server.Paging.Impl.PageImpl;
import server.Paging.Page;
import server.Paging.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StudentRepositoryImpl implements RepositoryInterface<Student> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Autowired
    private Validator<Student> studentValidator;

    @Override
    public Optional<Student> findOne(long id) {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        String sql = "select * from \"Students\"";
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("Name");
            String serialNumber = rs.getString("Name");
            int group = rs.getInt("Group");
            Student student = new Student(name, serialNumber, group);
            student.setId(id);
            return student;
        });
    }

    @Override
    public void save(Student entity) throws ValidatorException {
        String sql = "INSERT INTO Students VALUES(?,?,?,?)";
        studentValidator.validate(entity);
        jdbcOperations.update(sql, entity.getId(), entity.getName(), entity.getSerialNumber(), entity.getGroup());

    }

    @Override
    public void delete(long id) {
        String sql = "delete from \"Students\" where id=?";
        jdbcOperations.update(sql, id);
    }

    @Override
    public void update(Student entity) throws ValidatorException {
        String sql = "update \"Students\" set \"Group\"=?, \"SerialNumber\"=?, \"Name\"=? where id=?";
        studentValidator.validate(entity);
        jdbcOperations.update(sql, entity.getGroup(), entity.getSerialNumber(), entity.getName(), entity.getId());
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        List<Student> entities = findAll();
        AtomicInteger counter = new AtomicInteger(0);
        List<Student> l = (new ArrayList<>(entities.stream()//.sorted(Comparator.comparing(it -> it.getId()))
                .collect(Collectors.groupingBy(it -> (counter.getAndIncrement()) / pageable.getPageSize()))
                .values()).get(pageable.getPageNumber()));

        return new PageImpl<>(pageable, l.stream());
    }
}
