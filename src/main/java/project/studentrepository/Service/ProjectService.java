package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.FAMSSProject;
import project.studentrepository.Model.FBMASProject;
import project.studentrepository.Repository.FAMSSRepository;
import project.studentrepository.Repository.FBMASRepository;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    FAMSSRepository famssRepository;

    @Autowired
    FBMASRepository fbmasRepository;

    public List<FBMASProject> getProjectsForFBMAS() {
        return fbmasRepository.findAll();
    }

    public List<FAMSSProject> getProjectsForFAMSS() {
        return famssRepository.findAll();
    }

    public FAMSSProject saveProject(FAMSSProject famssProject) {
        return famssRepository.save(famssProject);
    }

                    // The Issue is here
//    public List<FAMSSProject> searchProjects(String department, Integer year, String supervisor,
//                                             String title) {
//        if (department != null && year != null) { // If it's searched by both
//            return famssRepository.findByDepartmentAndYear(department, year);
//        }
//        else if (year != null) { // If it's searched by department
//            return famssRepository.findByYear(year);
//        }
//        else if (department != null) {
//            return famssRepository.findByDepartment(department);
//        }
//        else if (supervisor != null) { // If it's searched by supervisor
//            return famssRepository.findBySupervisor(supervisor);
//
//        } else if (title != null) {
//            return famssRepository.findByTitleContaining(title);
//        }
//        else{
//            return Collections.emptyList();
//        }

    public List<FAMSSProject> searchProjectsDept(String department) {
        return famssRepository.findProjectsByDepartment(department);
    }
    public List<FAMSSProject> searchProjectYear(Integer year){
        return famssRepository.findByYear(year);
    }
    public List<FAMSSProject> searchProjectTitle(String title){
        return famssRepository.findByTitle(title);
    }
    public List<FAMSSProject> searchProjectSupervior(String supervisor){
        return  famssRepository.findBySupervisor(supervisor);
    }
//                                 FOR FBMAS PROJECT

        public List<FBMASProject> projectsSearch(String department, Integer year, String supervisor,
                                              String title){
        if(department != null && year != null){ // If it's searched by both
            return fbmasRepository.findByDepartmentAndYear(department, year);
        } else if (department != null) { // If it's searched by department
            return  fbmasRepository.findByDepartment(department);
        }
        else if (year != null){ // If it's searched by year
            return  fbmasRepository.findByYear(year);
        } else if (supervisor != null) { // If it's searched by supervisor
            return fbmasRepository.findBySupervisor(supervisor);
        } else if (title != null) {
            return  fbmasRepository.findByTitleContaining(title);
        }else{
          return  fbmasRepository.findAll(); // If none, return all the project
        }
        }



}
