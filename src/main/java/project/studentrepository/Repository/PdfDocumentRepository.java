package project.studentrepository.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.PdfDocument;

import java.util.List;

@Repository
public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
    List<PdfDocument> findByFacultyId(Long facultyId);
    
    @Query("SELECT p FROM PdfDocument p WHERE UPPER(p.faculty.abbreviation) = UPPER(:abbreviation)")
    List<PdfDocument> findByFacultyAbbreviationIgnoreCase(@Param("abbreviation") String abbreviation);
}

