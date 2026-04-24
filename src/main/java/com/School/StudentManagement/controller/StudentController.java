package com.School.StudentManagement.controller;

import com.School.StudentManagement.Util.JwtUtil;
import com.School.StudentManagement.dto.StudentSavedto;
import com.School.StudentManagement.dto.Studentdto;
import com.School.StudentManagement.dto.UserDto;
import com.School.StudentManagement.entity.Student;
import com.School.StudentManagement.service.StudentService;
import com.School.StudentManagement.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/save")
    public String saveStudent(@RequestBody StudentSavedto studentSaveDTO)
    {
        return studentService.addStudent(studentSaveDTO);
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents()
    {
        return studentService.getAllStudents();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id)
    {
        studentService.deletedata(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Studentdto studentdto)
    {
        studentService.update(studentdto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto)
    {
        try{
            /* 1. we explicitly use authentication manager and this will automatically use loadUserByUsername() and
                   passwordEncoder() for authentication , if there  is a valid user then we will go otherwise it will
                   throw an exception    */ //note:iski bean bnani padegi hame securityconfig me
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPass()));


            /* 2. takes user details object ,basically it uses to give info to jwt to create token like roles,etc ,but here
            we only use name for now*/
            UserDetails userDetails = userDetailService.loadUserByUsername(userDto.getUsername());


            //3. generate token using username and returning it
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){}
        return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
    }


}
