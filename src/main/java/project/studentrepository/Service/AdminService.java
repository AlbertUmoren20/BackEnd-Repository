package project.studentrepository.Service;

import project.studentrepository.DTO.ProjectDTO;
import project.studentrepository.DTO.ProjectStatisticsDTO;
import project.studentrepository.Model.Faculty;

import java.util.List;

public interface AdminService {
    List<Faculty> getAllFaculties();
    List<ProjectStatisticsDTO> getProjectStats(Integer year, String faculty);
    List<ProjectDTO> getAllProjects();
    ProjectDTO updateProject(Long id, ProjectDTO projectDTO, String projectType);
    boolean deleteProject(Long id, String projectType);
    boolean deleteProjectById(Long id);
}

