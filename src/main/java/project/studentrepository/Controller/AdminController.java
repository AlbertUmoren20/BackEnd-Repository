package project.studentrepository.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.studentrepository.DTO.FacultyDTO;
import project.studentrepository.DTO.ProjectDTO;
import project.studentrepository.DTO.ProjectStatisticsDTO;
import project.studentrepository.Model.Faculty;
import project.studentrepository.Response.DeleteResponse;
import project.studentrepository.Service.AdminService;
import project.studentrepository.Service.FacultyService;

import java.util.List;

@CrossOrigin(
    origins ="https://repository-react-iota.vercel.app/",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS} 
)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private FacultyService facultyService;

    @PostMapping("/addFaculty")
    public ResponseEntity<Faculty> addFaculty(@RequestBody FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyDTO.getName());
        faculty.setAbbreviation(facultyDTO.getAbbreviation());
        faculty.setDescription(facultyDTO.getDescription());
        Faculty savedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(savedFaculty);
    }

    @GetMapping("/getFaculties")
    public ResponseEntity<List<Faculty>> getFaculties() {
        List<Faculty> faculties = adminService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/getProjectStats")
    public ResponseEntity<List<ProjectStatisticsDTO>> getProjectStats(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String faculty) {
        List<ProjectStatisticsDTO> stats = adminService.getProjectStats(year, faculty);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = adminService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectDTO projectDTO) {
        String projectType = projectDTO.getProjectType();
        if (projectType == null || projectType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ProjectDTO updatedProject = adminService.updateProject(id, projectDTO, projectType);
        if (updatedProject.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<DeleteResponse> deleteProject(
            @PathVariable Long id,
            @RequestParam(required = false) String projectType) {
        try {
            boolean deleted;
            if (projectType != null && !projectType.isEmpty()) {
                // Use projectType if provided
                deleted = adminService.deleteProject(id, projectType);
            } else {
                // Try to find and delete from any repository
                deleted = adminService.deleteProjectById(id);
            }
            
            if (deleted) {
                DeleteResponse response = new DeleteResponse(true, "Project deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                DeleteResponse response = new DeleteResponse(false, "Failed to delete Project");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            DeleteResponse response = new DeleteResponse(false, "Failed to delete Project");
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/deleteFaculty/{id}")
    public ResponseEntity<DeleteResponse> deleteFaculty(@PathVariable Long id) {
        try {
            boolean deleted = facultyService.deleteFaculty(id);
            if (deleted) {
                DeleteResponse response = new DeleteResponse(true, "Faculty deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                DeleteResponse response = new DeleteResponse(false, "Failed to delete Faculty");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            DeleteResponse response = new DeleteResponse(false, "Failed to delete Faculty");
            return ResponseEntity.ok(response);
        }
    }
}

