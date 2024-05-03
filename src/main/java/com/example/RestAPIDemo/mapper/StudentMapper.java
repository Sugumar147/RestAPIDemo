package com.example.RestAPIDemo.mapper;

import com.example.RestAPIDemo.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM students")
    List<Student> findAllStudents();
    @Insert("INSERT INTO students (name, age, course) VALUES (#{name}, #{age}, #{course})")
    void insert(Student student);

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student findById(@Param("id") int id);

    @Update("UPDATE students SET name = #{name}, age = #{age}, course = #{course} WHERE id = #{id}")
    void update(Student student);

    @Delete("DELETE FROM students WHERE id = #{id}")
    void delete(@Param("id") int id);
}
