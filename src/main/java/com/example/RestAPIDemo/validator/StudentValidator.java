package com.example.RestAPIDemo.validator;

import com.example.RestAPIDemo.model.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

public class StudentValidator {

    private final Validator validator;

    public StudentValidator(Validator validator) {
        this.validator = validator;
    }

    public void validateStudentId(String id) {

        try {
            int idNumber = Integer.parseInt(id);
            if (idNumber < 0) {
                throw new Exception("Invalid input: ID cannot be negative", null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void validateStudent(Student student) throws ConstraintViolationException {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        Set<? extends jakarta.validation.ConstraintViolation<?>> convertedViolations = violations.stream()
                .map(violation -> (jakarta.validation.ConstraintViolation<?>) violation)
                .collect(Collectors.toSet());

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation error", convertedViolations);
        }
    }
}
