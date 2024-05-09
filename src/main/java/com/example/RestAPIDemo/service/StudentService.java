package com.example.RestAPIDemo.service;

import com.example.RestAPIDemo.DTO.StudentDTO;
import com.example.RestAPIDemo.mapper.StudentMapper;
import com.example.RestAPIDemo.mapper.StudentObjectMapper;
import com.example.RestAPIDemo.model.Student;
import com.example.RestAPIDemo.validator.StudentValidator;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {
    private final StudentMapper studentMapper;
    private final StudentValidator studentValidator;
    private final StudentObjectMapper studentObjectMapper;
    @Autowired
    public StudentService(StudentMapper studentMapper, Validator validator, StudentObjectMapper studentObjectMapper) {
        this.studentMapper = studentMapper;
        this.studentValidator = new StudentValidator(validator);
        this.studentObjectMapper = new StudentObjectMapper();
    }
    public List<StudentDTO> getStudents() {
        List<Student> studentList = studentMapper.findAllStudents();
        List<StudentDTO> studentDTOList = new ArrayList<>();

        for (Student student : studentList) {
            StudentDTO studentDTO = studentObjectMapper.toDTO(student);
            studentDTOList.add(studentDTO);
        }

        return studentDTOList;
    }

    public Student createStudent(Student student) {
        studentMapper.insert(student);
        return student;
    }

    // Update an existing student
    public StudentDTO updateStudent(int id, Student student) {
        studentValidator.validateStudentId(id);
        Student existingStudent = studentMapper.findById(id);
        if (existingStudent != null) {
            student.setId(id);
            studentMapper.update(student);
            StudentDTO studentDTO = studentObjectMapper.toDTO(student);

            // Return the StudentDTO object
            return studentDTO;
        }
        throw new NoSuchElementException();
    }

    // Delete a student by ID
    public void deleteStudent(int id){
        studentValidator.validateStudentId(id);
        Student existingStudent = studentMapper.findById(id);
        if (existingStudent == null) {
           throw new NoSuchElementException("The USER_ID not available in the database");
        }
        studentMapper.delete(id);
    }
}
