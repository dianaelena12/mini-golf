package common;

import common.Domain.Assignment;
import common.Domain.Problem;
import common.Domain.Student;

import java.util.Set;
import java.util.concurrent.Future;

public interface ServiceInterface{
    String SERVER_HOST = "localhost";
    int SERVER_PORT = 1234;

    String ADD_STUDENT = "ADD_STUDENT";
    void addStudent(Student student);

    String GET_ALL_STUDENTS = "GET_ALL_STUDENTS";
    Future<Set<Student>> getAllStudents();

    String REMOVE_STUDENT = "REMOVE_STUDENT";
    void removeStudent(Long id);

    String UPDATE_STUDENT = "UPDATE_STUDENT";
    void updateStudent(Student student);

    String ADD_PROBLEM = "ADD_PROBLEM";
    void addProblem(Problem problem);

    String GET_ALL_PROBLEMS = "GET_ALL_PROBLEMS";
    Future<Set<Problem>> getAllProblems();

    String REMOVE_PROBLEM = "REMOVE_PROBLEM";
    void removeProblem(Long id);

    String UPDATE_PROBLEM = "UPDATE_PROBLEM";
    void updateProblem(Problem problem);

    String ADD_ASSIGNMENT = "ADD_ASSIGNMENT";
    void addAssignment(Assignment assignment);

    String ASSIGN_GRADE = "ASSIGN_GRADE";
    void assignGrade(Long studentID, Long problemID, int grade);

    String GET_ALL_ASSIGNMENTS = "GET_ALL_ASSIGNMENTS";
    Future<Set<Assignment>> getAllAssignments();

    String GET_BY_GROUP = "GET_BY_GROUP";
    Future<Set<Student>> getAllStudentsByGroup(int group);

    String GET_BY_DIFFICULTY = "GET_BY_DIFFICULTY";
    Future<Set<Problem>> getAllProblemsByDifficulty(String difficulty);

    String GET_UNGRADED = "GET_UNGRADED";
    Future<Set<Assignment>> getUngradedAssignments();

    String SET_SIZE = "SET_SIZE";
    void setPageSize(int size);

    String GET_NEXT_STUDENTS = "GET_NEXT_STUDENTS";
    Future<Set<Student>> getNextStudents();

    String GET_NEXT_PROBLEMS = "GET_NEXT_PROBLEMS";
    Future<Set<Problem>> getNextProblems();

    String GET_NEXT_ASSIGNMENTS = "GET_NEXT_ASSIGNMENTS";
    Future<Set<Assignment>> getNextAssignments();
}