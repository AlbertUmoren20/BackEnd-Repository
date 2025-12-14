package project.studentrepository.Service;

import project.studentrepository.Model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    List<Faculty> getAllFaculties();
    boolean deleteFaculty(Long id);
}

