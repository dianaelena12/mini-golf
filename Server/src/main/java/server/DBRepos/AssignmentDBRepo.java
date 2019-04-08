//package server.DBRepos;
//
//
//import common.Domain.Assignment;
//import common.Domain.Validators.Validator;
//import server.DBRepo;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//public class AssignmentDBRepo extends DBRepo<Long, Assignment> {
//    public AssignmentDBRepo(Validator<Assignment> validator) {
//        super(validator);
//    }
//
//    @Override
//    public Optional<Assignment> saveInDB(Assignment entity) {
//        String sql = "insert into \"Assignments\"(\"StudentID\",\"ProblemID\",\"Grade\",id) values(?,?,?,?)";
//        try (Connection connect = this.connectToDB();
//             PreparedStatement statement = connect.prepareStatement(sql)) {
//
//            statement.setLong(1, entity.getStudentID());
//            statement.setLong(2, entity.getProblemID());
//            statement.setInt(3, entity.getGrade());
//            statement.setLong(4, entity.getId());
//
//            statement.executeUpdate();
//            return Optional.empty();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return Optional.of(entity);
//    }
//
//    @Override
//    public Optional<Assignment> deleteFromDB(Long id) {
//        Optional<Assignment> Assignment = this.getFromDB(id);
//        String sql = "delete from \"Assignments\" where id=?";
//        try (Connection connect = this.connectToDB();
//             PreparedStatement statement = connect.prepareStatement(sql)) {
//
//            statement.setLong(1, id);
//
//            statement.executeUpdate();
//
//            return Optional.empty();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return Assignment;
//    }
//
//    @Override
//    public Optional<Assignment> updateInDB(Assignment entity) {
//        String sql = "update \"Assignments\" set \"StudentID\"=?, \"ProblemID\"=?, \"Grade\"=? where id=?";
//        try (Connection connect = this.connectToDB();
//             PreparedStatement statement = connect.prepareStatement(sql)) {
//
//            statement.setLong(1, entity.getStudentID());
//            statement.setLong(2, entity.getProblemID());
//            statement.setInt(3, entity.getGrade());
//            statement.setLong(4, entity.getId());
//
//            statement.executeUpdate();
//
//            return Optional.of(entity);
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Assignment> getFromDB(Long id) {
//        String sql = "select * from \"Assignments\" where id=?";
//        try (Connection connect = this.connectToDB();
//             PreparedStatement statement = connect.prepareStatement(sql)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                Assignment Assignment = new Assignment(resultSet.getLong("StudentID"),
//                        resultSet.getLong("ProblemID"), resultSet.getInt("Grade"));
//                Assignment.setId(resultSet.getLong("id"));
//                return Optional.of(Assignment);
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Set<Assignment> findAllFromDB() {
//        Set<Assignment> Assignments = new HashSet<>();
//        String sql = "select * from \"Assignments\"";
//        try (Connection connect = this.connectToDB();
//             PreparedStatement statement = connect.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()
//        ) {
//            while (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                Long studID = resultSet.getLong("StudentID");
//                Long problemID = resultSet.getLong("ProblemID");
//                int grade = resultSet.getInt("Grade");
//
//                Assignment Assignment = new Assignment(studID, problemID, grade);
//                Assignment.setId(id);
//                Assignments.add(Assignment);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return Assignments;
//    }
//}
