package web.converter;

import core.model.Student;
import org.springframework.stereotype.Component;
import web.dto.StudentDto;

import java.util.Collection;

@Component
public class StudentConverter extends BaseConverter<Student, StudentDto> {
    @Override
    public StudentDto convertModelsToDtos(Collection<Student> students) {
        StudentDto studentDto = new StudentDto(
                student.getSerialNumber(),
                student.getName(),
                student.getGr()
        );
        studentDto.setId(student.getId());
        return studentDto;
    }

    public Student convertDtoToModel(StudentDto dto) {
        Student student = new StudentConverter(
                dto.getSerialNumber(),
                dto.getName(),
                dto.getGroup()
        );
        student.setId(dto.getId());
        return student;
    }
}
