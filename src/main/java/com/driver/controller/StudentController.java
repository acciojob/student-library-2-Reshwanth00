package com.driver.controller;

import com.driver.models.Student;
import com.driver.security.AuthorityConstants;
import com.driver.security.User;
import com.driver.security.UserRepository;
import com.driver.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/studentByEmail")
    public ResponseEntity getStudentByEmail(@RequestParam("email") String email){
        Student obj = studentService.getDetailsByEmail(email);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/studentById")
    public ResponseEntity getStudentById(@RequestParam("id") int id){
        Student obj = studentService.getDetailsById(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @PostMapping("/createStudent")
    public ResponseEntity createStudent(@RequestBody Student student){
        studentService.createStudent(student);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user =  User.builder()
                .username(student.getEmailId())
                .password(encoder.encode("pass1234"))
                .authority(AuthorityConstants.STUDENT_AUTHORITY)
                .build();
        userRepository.save(user);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
    @PutMapping("/updateStudent")
    public ResponseEntity updateStudent(@RequestBody Student student){
        studentService.updateStudent(student);
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/deleteStudent")
    public ResponseEntity deleteStudent(@RequestParam("id") int id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }
}
