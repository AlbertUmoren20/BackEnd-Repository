package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.DTO.ProjectDTO;
import project.studentrepository.DTO.ProjectStatisticsDTO;
import project.studentrepository.Model.*;
import project.studentrepository.Repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private FAMSSRepository famssRepository;

    @Autowired
    private FBMASRepository fbmasRepository;

    @Autowired
    private PdfFamssRepository pdfFamssRepository;

    @Autowired
    private PdfFbmasRepository pdfFbmasRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public List<ProjectStatisticsDTO> getProjectStats(Integer year, String faculty) {
        List<ProjectStatisticsDTO> stats = new ArrayList<>();

        // Query FAMSS projects
        if (faculty == null || faculty.equalsIgnoreCase("FAMSS")) {
            List<FAMSSProject> famssProjects;
            if (year != null) {
                famssProjects = famssRepository.findByYear(year);
            } else {
                famssProjects = famssRepository.findAll();
            }
            
            ProjectStatisticsDTO famssStat = new ProjectStatisticsDTO();
            famssStat.setFaculty("FAMSS");
            famssStat.setYear(year);
            famssStat.setCount(famssProjects.size());
            famssStat.setProjectType("FAMSS");
            stats.add(famssStat);

            // Query PdfFamss projects
            List<PdfFamss> pdfFamssProjects = pdfFamssRepository.findAll();
            if (year != null) {
                pdfFamssProjects = pdfFamssProjects.stream()
                    .filter(p -> p.getYear() != null && p.getYear().equals(year))
                    .toList();
            }
            
            ProjectStatisticsDTO pdfFamssStat = new ProjectStatisticsDTO();
            pdfFamssStat.setFaculty("FAMSS");
            pdfFamssStat.setYear(year);
            pdfFamssStat.setCount(pdfFamssProjects.size());
            pdfFamssStat.setProjectType("PdfFamss");
            stats.add(pdfFamssStat);
        }

        // Query FBMAS projects
        if (faculty == null || faculty.equalsIgnoreCase("FBMAS")) {
            List<FBMASProject> fbmasProjects;
            if (year != null) {
                fbmasProjects = fbmasRepository.findByYear(year);
            } else {
                fbmasProjects = fbmasRepository.findAll();
            }
            
            ProjectStatisticsDTO fbmasStat = new ProjectStatisticsDTO();
            fbmasStat.setFaculty("FBMAS");
            fbmasStat.setYear(year);
            fbmasStat.setCount(fbmasProjects.size());
            fbmasStat.setProjectType("FBMAS");
            stats.add(fbmasStat);

            // Query PdfFbmas projects
            List<PdfFbmas> pdfFbmasProjects = pdfFbmasRepository.findAll();
            if (year != null) {
                pdfFbmasProjects = pdfFbmasProjects.stream()
                    .filter(p -> p.getYear() != null && p.getYear().equals(year))
                    .toList();
            }
            
            ProjectStatisticsDTO pdfFbmasStat = new ProjectStatisticsDTO();
            pdfFbmasStat.setFaculty("FBMAS");
            pdfFbmasStat.setYear(year);
            pdfFbmasStat.setCount(pdfFbmasProjects.size());
            pdfFbmasStat.setProjectType("PdfFbmas");
            stats.add(pdfFbmasStat);
        }

        return stats;
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<ProjectDTO> allProjects = new ArrayList<>();

        // Get FAMSS projects
        List<FAMSSProject> famssProjects = famssRepository.findAll();
        for (FAMSSProject project : famssProjects) {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(project.getId());
            dto.setTitle(project.getTitle());
            dto.setDescription(project.getDescription());
            dto.setSupervisor(project.getSupervisor());
            dto.setDepartment(project.getDepartment());
            dto.setProjectBy(project.getProjectBy());
            dto.setYear(project.getYear());
            dto.setProjectType("FAMSS");
            allProjects.add(dto);
        }

        // Get FBMAS projects
        List<FBMASProject> fbmasProjects = fbmasRepository.findAll();
        for (FBMASProject project : fbmasProjects) {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(project.getId());
            dto.setTitle(project.getTitle());
            dto.setDescription(project.getDescription());
            dto.setSupervisor(project.getSupervisor());
            dto.setDepartment(project.getDepartment());
            dto.setProjectBy(project.getProjectBy());
            dto.setYear(project.getYear());
            dto.setProjectType("FBMAS");
            allProjects.add(dto);
        }

        // Get PdfFamss projects
        List<PdfFamss> pdfFamssProjects = pdfFamssRepository.findAll();
        for (PdfFamss project : pdfFamssProjects) {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(project.getId());
            dto.setTitle(project.getTitle());
            dto.setDescription(project.getDescription());
            dto.setSupervisor(project.getSupervisor());
            dto.setDepartment(project.getDepartment());
            dto.setProjectBy(project.getProjectBy());
            dto.setYear(project.getYear());
            dto.setProjectType("PdfFamss");
            allProjects.add(dto);
        }

        // Get PdfFbmas projects
        List<PdfFbmas> pdfFbmasProjects = pdfFbmasRepository.findAll();
        for (PdfFbmas project : pdfFbmasProjects) {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(project.getId());
            dto.setTitle(project.getTitle());
            dto.setDescription(project.getDescription());
            dto.setSupervisor(project.getSupervisor());
            dto.setDepartment(project.getDepartment());
            dto.setProjectBy(project.getProjectBy());
            dto.setYear(project.getYear());
            dto.setProjectType("PdfFbmas");
            allProjects.add(dto);
        }

        return allProjects;
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO, String projectType) {
        ProjectDTO updatedDTO = new ProjectDTO();

        switch (projectType.toUpperCase()) {
            case "FAMSS":
                Optional<FAMSSProject> famssOpt = famssRepository.findById(id);
                if (famssOpt.isPresent()) {
                    FAMSSProject famss = famssOpt.get();
                    famss.setTitle(projectDTO.getTitle());
                    famss.setDescription(projectDTO.getDescription());
                    famss.setSupervisor(projectDTO.getSupervisor());
                    famss.setDepartment(projectDTO.getDepartment());
                    famss.setProjectBy(projectDTO.getProjectBy());
                    famss.setYear(projectDTO.getYear());
                    FAMSSProject saved = famssRepository.save(famss);
                    updatedDTO.setId(saved.getId());
                    updatedDTO.setTitle(saved.getTitle());
                    updatedDTO.setDescription(saved.getDescription());
                    updatedDTO.setSupervisor(saved.getSupervisor());
                    updatedDTO.setDepartment(saved.getDepartment());
                    updatedDTO.setProjectBy(saved.getProjectBy());
                    updatedDTO.setYear(saved.getYear());
                    updatedDTO.setProjectType("FAMSS");
                }
                break;

            case "FBMAS":
                Optional<FBMASProject> fbmasOpt = fbmasRepository.findById(id);
                if (fbmasOpt.isPresent()) {
                    FBMASProject fbmas = fbmasOpt.get();
                    fbmas.setTitle(projectDTO.getTitle());
                    fbmas.setDescription(projectDTO.getDescription());
                    fbmas.setSupervisor(projectDTO.getSupervisor());
                    fbmas.setDepartment(projectDTO.getDepartment());
                    fbmas.setProjectBy(projectDTO.getProjectBy());
                    fbmas.setYear(projectDTO.getYear());
                    FBMASProject saved = fbmasRepository.save(fbmas);
                    updatedDTO.setId(saved.getId());
                    updatedDTO.setTitle(saved.getTitle());
                    updatedDTO.setDescription(saved.getDescription());
                    updatedDTO.setSupervisor(saved.getSupervisor());
                    updatedDTO.setDepartment(saved.getDepartment());
                    updatedDTO.setProjectBy(saved.getProjectBy());
                    updatedDTO.setYear(saved.getYear());
                    updatedDTO.setProjectType("FBMAS");
                }
                break;

            case "PDFFAMSS":
                Optional<PdfFamss> pdfFamssOpt = pdfFamssRepository.findById(id);
                if (pdfFamssOpt.isPresent()) {
                    PdfFamss pdfFamss = pdfFamssOpt.get();
                    pdfFamss.setTitle(projectDTO.getTitle());
                    pdfFamss.setDescription(projectDTO.getDescription());
                    pdfFamss.setSupervisor(projectDTO.getSupervisor());
                    pdfFamss.setDepartment(projectDTO.getDepartment());
                    pdfFamss.setProjectBy(projectDTO.getProjectBy());
                    pdfFamss.setYear(projectDTO.getYear());
                    PdfFamss saved = pdfFamssRepository.save(pdfFamss);
                    updatedDTO.setId(saved.getId());
                    updatedDTO.setTitle(saved.getTitle());
                    updatedDTO.setDescription(saved.getDescription());
                    updatedDTO.setSupervisor(saved.getSupervisor());
                    updatedDTO.setDepartment(saved.getDepartment());
                    updatedDTO.setProjectBy(saved.getProjectBy());
                    updatedDTO.setYear(saved.getYear());
                    updatedDTO.setProjectType("PdfFamss");
                }
                break;

            case "PDFFBMAS":
                Optional<PdfFbmas> pdfFbmasOpt = pdfFbmasRepository.findById(id);
                if (pdfFbmasOpt.isPresent()) {
                    PdfFbmas pdfFbmas = pdfFbmasOpt.get();
                    pdfFbmas.setTitle(projectDTO.getTitle());
                    pdfFbmas.setDescription(projectDTO.getDescription());
                    pdfFbmas.setSupervisor(projectDTO.getSupervisor());
                    pdfFbmas.setDepartment(projectDTO.getDepartment());
                    pdfFbmas.setProjectBy(projectDTO.getProjectBy());
                    pdfFbmas.setYear(projectDTO.getYear());
                    PdfFbmas saved = pdfFbmasRepository.save(pdfFbmas);
                    updatedDTO.setId(saved.getId());
                    updatedDTO.setTitle(saved.getTitle());
                    updatedDTO.setDescription(saved.getDescription());
                    updatedDTO.setSupervisor(saved.getSupervisor());
                    updatedDTO.setDepartment(saved.getDepartment());
                    updatedDTO.setProjectBy(saved.getProjectBy());
                    updatedDTO.setYear(saved.getYear());
                    updatedDTO.setProjectType("PdfFbmas");
                }
                break;
        }

        return updatedDTO;
    }

    @Override
    public boolean deleteProject(Long id, String projectType) {
        try {
            switch (projectType.toUpperCase()) {
                case "FAMSS":
                    if (famssRepository.existsById(id)) {
                        famssRepository.deleteById(id);
                        return true;
                    }
                    return false;
                case "FBMAS":
                    if (fbmasRepository.existsById(id)) {
                        fbmasRepository.deleteById(id);
                        return true;
                    }
                    return false;
                case "PDFFAMSS":
                    if (pdfFamssRepository.existsById(id)) {
                        pdfFamssRepository.deleteById(id);
                        return true;
                    }
                    return false;
                case "PDFFBMAS":
                    if (pdfFbmasRepository.existsById(id)) {
                        pdfFbmasRepository.deleteById(id);
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteProjectById(Long id) {
        try {
            // Try to find and delete from each repository
            if (famssRepository.existsById(id)) {
                famssRepository.deleteById(id);
                return true;
            }
            if (fbmasRepository.existsById(id)) {
                fbmasRepository.deleteById(id);
                return true;
            }
            if (pdfFamssRepository.existsById(id)) {
                pdfFamssRepository.deleteById(id);
                return true;
            }
            if (pdfFbmasRepository.existsById(id)) {
                pdfFbmasRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

