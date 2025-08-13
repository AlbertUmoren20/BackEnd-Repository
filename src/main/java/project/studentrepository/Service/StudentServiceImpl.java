package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.LoginDTO;
import project.studentrepository.Model.Student;
import project.studentrepository.Model.StudentDto;
import project.studentrepository.Repository.StudentRepository;
import project.studentrepository.Response.LoginResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public LoginResponse registerStudent(StudentDto studentDto) {
        if (studentDto == null) {
            return new LoginResponse("Invalid request", false);
        }

        Integer level = studentDto.getLevel();
        if (level == null || !(level == 100 || level == 200 || level == 300 || level == 400)) {
            return new LoginResponse("Academic level must be one of 100, 200, 300, 400", false);
        }

        if (studentDto.getEmail() == null || studentDto.getEmail().isBlank() ||
            studentDto.getPassword() == null || studentDto.getPassword().isBlank() ||
            studentDto.getFullname() == null || studentDto.getFullname().isBlank() ||
            studentDto.getMatricnumber() == null) {
            return new LoginResponse("All fields are required", false);
        }

        boolean emailExists = studentRepository.existsByEmail(studentDto.getEmail());
        boolean matricExists = studentRepository.existsByMatricnumber(studentDto.getMatricnumber());
        if (emailExists || matricExists) {
            return new LoginResponse("Account already exists, try logging in", false);
        }

        Student student = new Student();
        student.setFullname(studentDto.getFullname());
        student.setEmail(studentDto.getEmail());
        student.setMatricnumber(studentDto.getMatricnumber());
        student.setLevel(studentDto.getLevel());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        studentRepository.save(student);
        return new LoginResponse("Registration successful", true);
    }

    @Override
    public LoginResponse loginStudent(LoginDTO loginDTO) {
        Student student = studentRepository.findByEmail(loginDTO.getEmail());
        if (student == null) {
            return new LoginResponse("Email not found", false);
        }

        String rawPassword = loginDTO.getPassword();
        String storedPasswordHash = student.getPassword();
        int providedMatricNumber = loginDTO.getMatricnumber();
        int storedMatricNumber = student.getMatricnumber();

        boolean isPasswordCorrect = passwordEncoder.matches(rawPassword, storedPasswordHash);
        boolean isMatricNumberRight = providedMatricNumber == storedMatricNumber;

        if (isPasswordCorrect && isMatricNumberRight) {
            return new LoginResponse("Login Successful", true);
        } else if (!isPasswordCorrect) {
            return new LoginResponse("Password not matched", false);
        } else {
            return new LoginResponse("Matric number not matched", false);
        }
    }
}

