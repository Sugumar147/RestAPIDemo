package com.example.RestAPIDemo.mapper;

import com.example.RestAPIDemo.DTO.StudentDTO;
import com.example.RestAPIDemo.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentObjectMapper {
    public StudentDTO toDTO(Student student) {
        return new StudentDTO(student.getId(), student.getName(), student.getCourse());
    }

    public Student toEntity(StudentDTO studentDTO) {
        return new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getCourse());
    }
}
