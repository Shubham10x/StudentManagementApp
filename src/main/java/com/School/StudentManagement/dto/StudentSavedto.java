package com.School.StudentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentSavedto {
    private String studentname;
    private String address;
    private String phone;
    private String username;
    private String password;

}
