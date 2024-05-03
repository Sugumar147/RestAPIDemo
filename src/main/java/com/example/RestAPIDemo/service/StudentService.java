package com.example.RestAPIDemo.service;

import com.example.RestAPIDemo.configuration.StudentConfig;
import com.example.RestAPIDemo.mapper.StudentMapper;
import com.example.RestAPIDemo.model.Student;
import com.example.RestAPIDemo.validator.StudentValidator;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentMapper studentMapper;
    private final StudentValidator studentValidator;
    @Autowired
    public StudentService(StudentMapper studentMapper, Validator validator) {
        this.studentMapper = studentMapper;
        this.studentValidator = new StudentValidator(validator);
    }
    public List<Student> getStudents() {
        List<Student> studentList = studentMapper.findAllStudents();
        return studentList;
    }

    public Student createStudent(Student student) {
        studentMapper.insert(student);
        return student;
    }

    // Update an existing student
    public Student updateStudent(String id, Student student) {
        studentValidator.validateStudentId(id);
        int idNumber = Integer.parseInt(id);
        Student existingStudent = studentMapper.findById(idNumber);
        if (existingStudent != null) {
            student.setId(idNumber);
            studentMapper.update(student);
            return student;
        }
        return null;
    }

    // Delete a student by ID
    public boolean deleteStudent(String id) {
        studentValidator.validateStudentId(id);
        int idNumber = Integer.parseInt(id);
        Student existingStudent = studentMapper.findById(idNumber);
        if (existingStudent != null) {
            studentMapper.delete(idNumber);
            return true;
        }
        return false;
    }
}
