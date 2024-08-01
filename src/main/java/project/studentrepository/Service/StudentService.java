package project.studentrepository.Service;

import org.springframework.stereotype.Service;
import project.studentrepository.Model.LoginDTO;
import project.studentrepository.Model.Student;
import project.studentrepository.Model.StudentDto;
import project.studentrepository.Response.LoginResponse;

import java.util.List;

@Service
public interface StudentService {

//    Student findByEmail(String email);
    public Student saveStudent(Student student); //operation to Add students into the database
    public List<Student> getAllStudents(); //operation to get students from the database

    LoginResponse loginStudent(LoginDTO loginDto);

}
