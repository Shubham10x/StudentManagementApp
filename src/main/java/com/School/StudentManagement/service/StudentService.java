package com.School.StudentManagement.service;

import com.School.StudentManagement.dto.StudentSavedto;
import com.School.StudentManagement.dto.Studentdto;
import com.School.StudentManagement.entity.Student;

import java.util.List;


public interface StudentService {

    String addStudent(StudentSavedto studentSaveDTO);

    List<Student> getAllStudents();

     void deletedata(Integer id);

     void update(Studentdto studentdto);
}
