package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.LoginDTO;
import project.studentrepository.Model.Lecturer;
import project.studentrepository.Repository.LecturerRepository;
import project.studentrepository.Response.LoginResponse;

import java.util.List;
import java.util.Optional;

@Service
public class LecturerServiceImpl implements LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public Lecturer saveLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    @Override
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    @Override
    public LoginResponse loginLecturer(LoginDTO loginDTO) {
        Lecturer lecturer1 = lecturerRepository.findByEmail(loginDTO.getEmail());
        if (lecturer1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = lecturer1.getPassword();

            // Validate password against database - handle null cases
            Boolean isPwdRight = (password != null && encodedPassword != null) && password.equals(encodedPassword);

            if (isPwdRight) {
                Optional<Lecturer> lecturer = lecturerRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (lecturer.isPresent()) {
                    // Get user information
                    String userFullname = lecturer1.getFullname();
                    String userEmail = lecturer1.getEmail();
                    
                    // Detect lecturer by checking if email contains "lecturer" (case-insensitive)
                    String email = loginDTO.getEmail().toLowerCase();
                    String userRole = email.contains("lecturer") ? "lecturer" : "student";
                    
                    return new LoginResponse("Login Successful", true, null, userFullname, userRole, userEmail);
                } else {
                    return new LoginResponse("Email not found", false, null, null, null, null);
                }
            } else {
                return new LoginResponse("Password does not match", false, null, null, null, null);
            }
        }
        return new LoginResponse("Email not found", false, null, null, null, null);
    }
}

