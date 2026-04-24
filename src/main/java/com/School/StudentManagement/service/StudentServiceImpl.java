package com.School.StudentManagement.service;

import com.School.StudentManagement.dto.StudentSavedto;
import com.School.StudentManagement.dto.Studentdto;
import com.School.StudentManagement.entity.Student;
import com.School.StudentManagement.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepo studentRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public String addStudent(StudentSavedto studentSaveDTO) {

       Student student=new Student(
               studentSaveDTO.getStudentname(),
               studentSaveDTO.getAddress(),
               studentSaveDTO.getPhone(),
               studentSaveDTO.getUsername(),
               passwordEncoder.encode(studentSaveDTO.getPassword())
       );
       studentRepo.save(student);
       return student.getStudentname();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public void deletedata(Integer id)
    {
        studentRepo.deleteById(id);

    }

    public void update(Studentdto studentdto)
    {
        Student s=studentRepo.findById(studentdto.getStudentid()).get();
        s.setStudentname(studentdto.getStudentname());
        s.setAddress(studentdto.getAddress());
        s.setPhone(studentdto.getPhone());

        studentRepo.save(s);
    }
}
