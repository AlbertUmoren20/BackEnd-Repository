package project.studentrepository.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.PdfFamss;

import java.util.List;

@Repository
public interface PdfFamssRepository extends JpaRepository<PdfFamss, Long> {

    List<PdfFamss> getProjectById (Long id);
}
