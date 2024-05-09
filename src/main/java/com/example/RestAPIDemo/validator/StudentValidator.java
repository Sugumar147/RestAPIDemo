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

    public void validateStudentId(int id) throws ConstraintViolationException{

        try {
            if (id < 0) {
                throw new ConstraintViolationException("Invalid input: ID cannot be negative", null);
            }
        } finally {
            System.out.println("Validation success");
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
