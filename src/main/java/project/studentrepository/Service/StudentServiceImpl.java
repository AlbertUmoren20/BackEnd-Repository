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
        Student student1 = studentRepository.findByEmail(loginDTO.getEmail());
        if (student1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = student1.getPassword();
            int matricnumber = loginDTO.getMatricnumber();
            int storedMatricNumber = student1.getMatricnumber();

            // Validate password against database - handle null cases
            Boolean isPwdRight = (password != null && encodedPassword != null) && password.equals(encodedPassword);
            Boolean isMatricNumberRight = matricnumber == storedMatricNumber; // Matric number check

            if (isPwdRight && isMatricNumberRight) {
                Optional<Student> student = studentRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (student.isPresent()) {
                    // Get user information
                    Integer userLevel = student1.getLevel();
                    String userFullname = student1.getFullname();
                    
                    // Detect admin by checking if email contains "admin" (case-insensitive)
                    String email = loginDTO.getEmail().toLowerCase();
                    String userRole = email.contains("admin") ? "admin" : "student";
                    
                    return new LoginResponse("Login Successful", true, userLevel, userFullname, userRole);
                } else {
                    return new LoginResponse("Email not found", false, null, null, null); // Assuming this is the intended message
                }
            } else if (!isPwdRight) {
                return new LoginResponse("Password does not match", false, null, null, null);
            } else if (!isMatricNumberRight) {
                return new LoginResponse("Matric number not matched", false, null, null, null);
            }
        }
        return new LoginResponse("Email not found", false, null, null, null); // Assuming this is the intended message when student1 is null
    }
}

