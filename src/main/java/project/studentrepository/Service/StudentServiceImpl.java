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
        // Detect user type from email (contains "admin" or "lecturer")
        String email = student.getEmail();
        if (email != null) {
            String emailLower = email.toLowerCase();
            if (emailLower.contains("admin")) {
                student.setRole("admin");
                // Set matricnumber and level to null for admins
                student.setMatricnumber(null);
                student.setLevel(null);
            } else if (emailLower.contains("lecturer")) {
                student.setRole("lecturer");
                // Set matricnumber and level to null for lecturers
                student.setMatricnumber(null);
                student.setLevel(null);
            } else {
                student.setRole("student");
                // For students, matricnumber and level are optional (nullable)
                // They can be null, but if provided, they will be stored
            }   
        }
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

            // Validate password against database - handle null cases
            Boolean isPwdRight = (password != null && encodedPassword != null) && password.equals(encodedPassword);

            if (isPwdRight) {
                // Detect user type from email or stored role
                String email = loginDTO.getEmail().toLowerCase();
                String userRole = student1.getRole();
                
                // If role is not set, detect from email
                if (userRole == null || userRole.isEmpty()) {
                    if (email.contains("admin")) {
                        userRole = "admin";
                    } else if (email.contains("lecturer")) {
                        userRole = "lecturer";
                    } else {
                        userRole = "student";
                    }
                }
                
                // For students, optionally verify matricnumber if provided
                if ("student".equals(userRole)) {
                    String providedMatricNumber = loginDTO.getMatricnumber();
                    String storedMatricNumber = student1.getMatricnumber();
                    
                    // If matricnumber is provided in login, verify it matches
                    // Matric numbers can be alphanumeric and of any length
                    if (providedMatricNumber != null && storedMatricNumber != null) {
                        if (!providedMatricNumber.equals(storedMatricNumber)) {
                            return new LoginResponse("Matric number does not match", false, 
                                null, null, null, null, null);
                        }
                    }
                }
                
                Optional<Student> student = studentRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (student.isPresent()) {
                    // Get user information
                    Integer userLevel = student1.getLevel();
                    String userFullname = student1.getFullname();
                    String userEmail = student1.getEmail();
                    String userMatricnumber = student1.getMatricnumber();
                    
                    return new LoginResponse("Login Successful", true, userLevel, userFullname, userRole, userEmail, userMatricnumber);
                } else {
                    return new LoginResponse("Email not found", false, null, null, null, null, null);
                }
            } else {
                return new LoginResponse("Password does not match", false, null, null, null, null, null);
            }
        }
        return new LoginResponse("Email not found", false, null, null, null, null, null);
    }
}

