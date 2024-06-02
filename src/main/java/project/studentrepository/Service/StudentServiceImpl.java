package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.LoginDTO;
import project.studentrepository.Model.Student;
import project.studentrepository.Repository.StudentRepository;
import project.studentrepository.Response.LoginResponse;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @Override
//    public Student findByEmail(String email) {
//        return studentRepository.findByEmail(email);
//    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public LoginResponse loginStudent(LoginDTO loginDTO) {
        String msg = "";
        Student student1 = studentRepository.findByEmail(loginDTO.getEmail()); // Explain
        if (student1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = student1.getPassword();
            Boolean isPwdRight = password.matches(password);
            if (isPwdRight) {
                Optional<Student> student =
                        studentRepository.findOneByEmailAndPassword (loginDTO.getEmail(), encodedPassword);
                if (student.isPresent()) {
                    return new LoginResponse("Login Successful", true);
                } else {
                    return new LoginResponse("Email not found", false); // Assuming this is the intended message
                }
            } else {
                return new LoginResponse("Password not matched", false);
            }

        }
        return null;
    }
}


