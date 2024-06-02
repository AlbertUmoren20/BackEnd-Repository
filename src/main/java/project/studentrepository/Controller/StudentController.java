package project.studentrepository.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.studentrepository.Model.LoginDTO;
import project.studentrepository.Model.Student;
import project.studentrepository.Model.StudentDto;
import project.studentrepository.Response.LoginResponse;
import project.studentrepository.Service.StudentService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add") // Adds data
    public String add(@RequestBody Student student) {
        studentService.saveStudent(student); //Go into student service section and make this
        // operation
        return "New Student added";
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
//    @GetMapping("hello")
//    public String hello(){
//        return "hello, everyone!";
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody LoginDTO logindto) {
        LoginResponse loginResponse = studentService.loginStudent(logindto);
        return ResponseEntity.ok(loginResponse);
    }
}