package Repo.DBRepos;

import Domain.Student;
import Domain.Validators.Validator;
import Repo.DBRepo;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StudentDBRepo extends DBRepo<Long, Student> {

    public StudentDBRepo(Validator<Student> validator) {
        super(validator);
    }

    @Override
    public Optional<Student> saveInDB(Student entity) {
        String sql = "insert into \"Students\"(\"Group\", \"SerialNumber\", \"Name\", id) values (?,?,?,?)";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, entity.getGroup());
            statement.setString(2, entity.getSerialNumber());
            statement.setString(3, entity.getName());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Student> deleteFromDB(Long id) {
        Optional<Student> student = this.getFromDB(id);
        String sql = "delete from \"Students\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setLong(1, id);

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return student;
    }

    @Override
    public Optional<Student> updateInDB(Student entity) {
        String sql = "update \"Students\" set \"Group\"=?, \"SerialNumber\"=?, \"Name\"=? where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, entity.getGroup());
            statement.setString(2, entity.getSerialNumber());
            statement.setString(3, entity.getName());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> getFromDB(Long id) {
        String sql = "select * from \"Students\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student(resultSet.getString("SerialNumber"),
                        resultSet.getString("Name"), resultSet.getInt("Group"));
                student.setId(resultSet.getLong("id"));
                return Optional.of(student);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<Student> findAllFromDB() {
        Set<Student> students = new HashSet<>();
        String sql = "select * from \"Students\"";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("SerialNumber");
                String serialNumber = resultSet.getString("Name");
                int group = resultSet.getInt("Group");

                Student student = new Student(name, serialNumber, group);
                student.setId(id);
                students.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return students;
    }
}
