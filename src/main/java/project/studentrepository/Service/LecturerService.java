package project.studentrepository.Service;

import org.springframework.stereotype.Service;
import project.studentrepository.Model.Lecturer;
import project.studentrepository.Response.LoginResponse;
import project.studentrepository.Model.LoginDTO;

import java.util.List;

@Service
public interface LecturerService {
    Lecturer saveLecturer(Lecturer lecturer);
    List<Lecturer> getAllLecturers();
    LoginResponse loginLecturer(LoginDTO loginDto);
}

