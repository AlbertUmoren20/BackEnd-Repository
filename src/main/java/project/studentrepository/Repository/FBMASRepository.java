package project.studentrepository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.FBMASProject;
import java.util.List;

@Repository
public interface FBMASRepository extends JpaRepository<FBMASProject, Long> {

    List<FBMASProject> findByDepartmentAndYear(String department, Integer year);
    List<FBMASProject> findByDepartment(String department);
    List<FBMASProject> findByYear(Integer year);
    List<FBMASProject> findBySupervisor(String supervisor);
    List<FBMASProject> findByTitleContaining(String title);
}
