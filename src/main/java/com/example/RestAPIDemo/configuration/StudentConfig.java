package com.example.RestAPIDemo.configuration;

import com.example.RestAPIDemo.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class StudentConfig {
    @Bean
    public Student student1() {
        return new Student(1,"sugu",21,"CSE");
    }
    public Student student2() {
        return new Student(2,"shakthi",20,"CSE");
    }
    public Student student3() {
        return new Student(3,"arun",21,"IT");
    }
    public Student student4() {
        return new Student(4,"srimathi",20,"ECE");
    }
}
