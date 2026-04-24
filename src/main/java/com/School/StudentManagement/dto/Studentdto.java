package com.School.StudentManagement.dto;

import com.School.StudentManagement.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Studentdto{

    private int studentid;
    private String studentname;
    private String address;
    private String phone;

}