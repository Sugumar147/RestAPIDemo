package com.example.RestAPIDemo.controller;

import com.example.RestAPIDemo.DTO.StudentDTO;
import com.example.RestAPIDemo.model.Student;
import com.example.RestAPIDemo.service.StudentService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("students")
public class HomeController {

    @Autowired
    private StudentService studentService;
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudentList() {
        try {
            List<StudentDTO> studentList = studentService.getStudents();
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody(required = true) Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            System.out.println(e+" "+ "Student object is null");
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } catch (ConstraintViolationException e) {
            System.out.println(e+" "+ "Invalid input");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e+" "+ "Duplicate id");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e+" "+ "Server error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT: Update an existing student
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable(name = "id", required = true) int id, @RequestBody Student student) {
        try {
            StudentDTO updatedStudent = studentService.updateStudent(id, student);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e+" No Student objects found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException e) {
            System.out.println(e+" "+ "Invalid input");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e+" "+ "Duplicate id");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e + " Server error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE: Delete a student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable(name = "id", required = true) int id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MethodArgumentTypeMismatchException e) {
            System.err.println("Invalid type for ID parameter: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }  catch (NoSuchElementException e) {
            System.out.println(e+" No Student objects found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ConstraintViolationException e) {
            System.out.println(e + " " + "Invalid input");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e + " Server error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
