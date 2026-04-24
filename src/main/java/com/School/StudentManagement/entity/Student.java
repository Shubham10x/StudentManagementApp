package com.School.StudentManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="student_id",length = 11)
    private int studentid;

    @Column(name="student_name",length = 45)
    private String studentname;

    @Column(name="address",length = 60)
    private String address;

    @Column(name="phone",length = 12)
    private String phone;

    @Column(name="user_name")
    private String username;

    @Column(name="password")
    private String pass;

    @Column
    private List<String> roles;

    public Student(String studentname, String address, String phone,String Username,String Password) {
        this.studentname = studentname;
        this.address = address;
        this.phone = phone;
        this.username=Username;
        this.pass=Password;
        this.roles=List.of("USER");

    }

}
