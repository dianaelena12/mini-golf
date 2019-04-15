package springjpa.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springjpa.Domain.Student;
import springjpa.Repo.JPARepos.StudentRepoJPA;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(
            StudentServiceImpl.class);

    @Autowired
    private StudentRepoJPA studentRepository;

    @Override
    public List<Student> getAllStudents() {
        log.trace("getAllStudents --- method entered");

        List<Student> result = studentRepository.findAll();

        log.trace("getAllStudents: result={}", result);

        return result;
    }

    @Override
    public void saveStudent(Student student) {
        log.trace("saveStudent: student={}", student);

        studentRepository.save(student);

        log.trace("saveStudent --- method finished");
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
        log.trace("update: student={}", student);

        studentRepository.findById(student.getId())
                .ifPresent(student1 -> {
                    student1.setName(student.getName());
                    student1.setSerialNumber(student.getSerialNumber());
                    student1.setGr(student.getGr());
                    log.debug("update --- student updated? --- " +
                            "student={}", student1);
                });

        log.trace("update --- method finished");
    }

    @Override
    public void deleteStudent(Long id) {
        log.trace("delete: id={}", id);

        studentRepository.deleteById(id);

        log.trace("delete --- method finished");
    }
}
