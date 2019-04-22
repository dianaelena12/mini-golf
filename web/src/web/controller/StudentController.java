package web.controller;

import core.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.StudentConverter;
import web.dto.StudentDto;
import web.dto.StudentsDto;

import java.util.List;
import java.util.Set;

@RestController
public class StudentController {
    private static final Logger log =
            LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentConverter studentConverter;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public StudentsDto getAllStudents() {
        log.trace("getAllStudents --- method entered");
        List<Student> studentList = studentService.getAllStudents();
        Set<StudentDto> dtoSet = studentConverter.convertModelsToDtos(studentList);

        StudentsDto result = new StudentsDto(dtoSet);

        log.trace("getAllStudents: result={}", result);

        return result;

    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    StudentDto saveStudent(@RequestBody StudentDto dto) {
        log.trace("saveStudent: dto={}", dto);

        Student saved = this.studentService.saveStudent(
                studentConverter.convertDtoToModel(dto)
        );
        StudentDto result = studentConverter.convertModelToDto(saved);

        log.trace("saveStudent: result={}", result);

        return result;
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    StudentDto updateStudent(@PathVariable Long id,
                             @RequestBody StudentDto dto) {
        log.trace("updateStudent: id={},dto={}", id, dto);

        Student update = studentService.updateStudent(
                id,
                studentConverter.convertDtoToModel(dto));
        StudentDto result = studentConverter.convertModelToDto(update);

        return result;
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        log.trace("deleteStudent: id={}", id);

        studentService.deleteStudent(id);

        log.trace("deleteStudent --- method finished");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
