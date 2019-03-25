package Repo.DBRepos;

import Domain.Problem;
import Domain.Validators.Validator;
import Repo.DBRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProblemDBRepo extends DBRepo<Long, Problem> {

    public ProblemDBRepo(Validator<Problem> validator) {
        super(validator);
    }

    @Override
    public Optional<Problem> saveInDB(Problem entity) {
        String sql = "insert into \"Problems\"(\"Subject\",\"Difficulty\",\"Text\",id) values(?,?,?,?)";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, entity.getSubject());
            statement.setString(2, entity.getDifficulty());
            statement.setString(3, entity.getText());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Problem> deleteFromDB(Long id) {
        Optional<Problem> problem = this.getFromDB(id);
        String sql = "delete from \"Problems\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setLong(1, id);

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return problem;
    }

    @Override
    public Optional<Problem> updateInDB(Problem entity) {
        String sql = "update \"Problems\" set \"Subject\"=?, \"Difficulty\"=?, \"Text\"=? where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, entity.getSubject());
            statement.setString(2, entity.getDifficulty());
            statement.setString(3, entity.getText());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();

            return Optional.of(entity);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Problem> getFromDB(Long id) {
        String sql = "select * from \"Problems\" where id=?";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Problem problem = new Problem(resultSet.getString("Subject"),
                        resultSet.getString("Difficulty"), resultSet.getString("Text"));
                problem.setId(resultSet.getLong("id"));
                return Optional.of(problem);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Set<Problem> findAllFromDB() {
        Set<Problem> problems = new HashSet<>();
        String sql = "select * from \"Problems\"";
        try (Connection connect = this.connectToDB();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String subject = resultSet.getString("Subject");
                String difficulty = resultSet.getString("Difficulty");
                String text = resultSet.getString("Text");

                Problem problem = new Problem(subject, difficulty, text);
                problem.setId(id);
                problems.add(problem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return problems;
    }
}
