package com.example.RestAPIDemo.service;

import com.example.RestAPIDemo.DTO.StudentDTO;
import com.example.RestAPIDemo.mapper.StudentMapper;
import com.example.RestAPIDemo.mapper.StudentObjectMapper;
import com.example.RestAPIDemo.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentMapper studentMapper;
    @Autowired
    private StudentObjectMapper studentObjectMapper;
    @Captor
    private ArgumentCaptor<Student> studentCaptor;

    @BeforeEach
    public void setUp() {
        // Initializes the annotated mocks and injects them into the class being tested
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getAllStudentsTest() {
        Student student1 = new Student(1, "Alice", 20, "CS");
        Student student2 = new Student(2, "Bob", 21, "IT");
        List<Student> studentList = Arrays.asList(student1, student2);

        StudentDTO studentDTO1 = new StudentDTO(1, "Alice",  "CS");
        StudentDTO studentDTO2 = new StudentDTO(2, "Bob",  "IT");
        List<StudentDTO> expectedStudentDTOList = Arrays.asList(studentDTO1, studentDTO2);

        when(studentMapper.findAllStudents()).thenReturn(studentList);

        List<StudentDTO> result = studentService.getStudents();
        verify(studentMapper).findAllStudents();
        assertEquals(result.get(0).getName(),expectedStudentDTOList.get(0).getName());
        assertEquals(result.get(0).getId(),expectedStudentDTOList.get(0).getId());
        assertEquals(result.get(0).getCourse(),expectedStudentDTOList.get(0).getCourse());
    }

    @Test
    void isStudentInserted() {
        Student student = new Student(1,"xyz",21,"CCE");
        studentService.createStudent(student);
        verify(studentMapper).insert(studentCaptor.capture());
        Student result = studentCaptor.getValue();
        assertEquals(student.getName(),result.getName());
        assertEquals(student.getAge(),result.getAge());
        assertEquals(student.getCourse(),result.getCourse());
        assertEquals(student.getId(),result.getId());
    }

    @Test
    void isStudentUpdateSuccessWhenUserId_Exists() {
        int id = 1;
        Student student = new Student(id, "John Doe", 25, "A");
        StudentDTO expectedStudentDTO = new StudentDTO(id, "John Doe", "A");

        when(studentMapper.findById(id)).thenReturn(student);

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        StudentDTO result = studentService.updateStudent(id, student);

        verify(studentMapper).findById(id);
        verify(studentMapper).update(studentCaptor.capture());
        assertEquals(result.getName(),expectedStudentDTO.getName());
        Student updatedStudent = studentCaptor.getValue();
        assertEquals(updatedStudent.getId(),id);
        assertEquals(updatedStudent.getName(),student.getName());
        assertEquals(updatedStudent.getAge(),student.getAge());
    }

    @Test
    void isStudentUpdateFailedWhenUserId_NotExists() {
        Student updatedStudent = new Student(1,"xyz",21,"CCE");
        when(studentMapper.findById(1)).thenReturn(null);
        assertThrows(
                NoSuchElementException.class,
                () -> studentService.updateStudent(1, updatedStudent)
        );

        // Verify that the update method was not called since the student ID does not exist
        verify(studentMapper, never()).update(updatedStudent);
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