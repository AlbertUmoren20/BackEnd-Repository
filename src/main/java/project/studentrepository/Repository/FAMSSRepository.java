package project.studentrepository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.FAMSSProject;
import java.util.List;

@Repository
public interface FAMSSRepository extends JpaRepository<FAMSSProject, Long> {

    // Search by department
    List<FAMSSProject> findByYear (Integer year); // search by year
    List<FAMSSProject> findBySupervisor (String supervisor); // search by supervisor
    List<FAMSSProject> findByDepartmentAndYear(String department, Integer year);
    List<FAMSSProject> findByTitleContaining(String title);
    List<FAMSSProject> findProjectsByDepartment(String department);
    List<FAMSSProject> findByTitle(String title);
    List<FAMSSProject> findByDepartment(String department);
}
