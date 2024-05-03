package com.example.RestAPIDemo.service;

import com.example.RestAPIDemo.mapper.StudentMapper;
import com.example.RestAPIDemo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentMapper studentMapper;


    @BeforeEach
    public void setUp() {
        // Initializes the annotated mocks and injects them into the class being tested
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllStudentsTest() {
        Student student1 = new Student(3,"sugu",20,"CS");
        Student student2 = new Student(4, "sk", 21, "IT");
        List<Student> studentList = Arrays.asList(student1,student2);
        when(studentMapper.findAllStudents()).thenReturn(studentList);
        List<Student> result = studentService.getStudents();
        assertEquals(studentList,result);
        assertEquals(studentList.getFirst().getName(),result.getFirst().getName());
        verify(studentMapper,times(1)).findAllStudents();
    }

    @Test
    void isStudentInserted() {
        Student student = new Student(1,"xyz",21,"CCE");
        Student result = studentService.createStudent(student);
        assertEquals(student,result);
    }

    @Test
    void isStudentUpdateSuccessWhenUserId_Exists() {
        Student student = new Student(1,"xyz",21,"CCE");
        Student updatedStudent = new Student(1,"xyz",22,"CSE");
        when(studentMapper.findById(1)).thenReturn(student);
        Student result = studentService.updateStudent(1,updatedStudent);
        assertEquals(result,updatedStudent);
        verify(studentMapper,times(1)).update(updatedStudent);
    }

    @Test
    void isStudentUpdateFailedWhenUserId_NotExists() {
        Student updatedStudent = new Student(1,"xyz",21,"CCE");
        when(studentMapper.findById(1)).thenReturn(null);
        Student result = studentService.updateStudent(1,updatedStudent);
        assertNull(result);
        verify(studentMapper,times(0)).update(updatedStudent);
    }

//    @Test
//    void isStudentSuccessfullyDeletedWhenUserId_Exists() {
//        Student student = new Student(1,"xyz",21,"CCE");
//        when(studentMapper.findById(1)).thenReturn(student);
//        boolean result = studentService.deleteStudent(1);
//        assertTrue(result);
//        verify(studentMapper,times(1)).delete(1);
//    }


//    @Test
//    void isStudentNotDeletedSuccessfullyWhenUserId_NotExists() {
//        Student student = new Student(1,"xyz",21,"CCE");
//        when(studentMapper.findById(1)).thenReturn(null);
//        boolean result = studentService.deleteStudent(1);
//        assertFalse(result);
//        verify(studentMapper,times(0)).delete(1);
//    }
}