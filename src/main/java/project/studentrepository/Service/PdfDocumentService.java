package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.Faculty;
import project.studentrepository.Model.PdfDocument;
import project.studentrepository.Repository.FacultyRepository;
import project.studentrepository.Repository.PdfDocumentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PdfDocumentService {

    private final PdfDocumentRepository pdfDocumentRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public PdfDocumentService(PdfDocumentRepository pdfDocumentRepository, 
                             FacultyRepository facultyRepository) {
        this.pdfDocumentRepository = pdfDocumentRepository;
        this.facultyRepository = facultyRepository;
    }

    public PdfDocument uploadPdfFile(PdfDocument pdfDocument) {
        return pdfDocumentRepository.save(pdfDocument);
    }

    public List<PdfDocument> getUploadsByFaculty(Long facultyId) {
        return pdfDocumentRepository.findByFacultyId(facultyId);
    }

    public List<PdfDocument> getUploadsByFacultyAbbreviation(String abbreviation) {
        return pdfDocumentRepository.findByFacultyAbbreviationIgnoreCase(abbreviation);
    }

    public List<PdfDocument> getAllUploads() {
        return pdfDocumentRepository.findAll();
    }

    public Optional<Faculty> findFacultyByNameOrAbbreviation(String nameOrAbbreviation) {
        // First try to find by abbreviation (case-insensitive)
        Optional<Faculty> byAbbreviation = facultyRepository.findByAbbreviation(
            nameOrAbbreviation.toUpperCase());
        if (byAbbreviation.isPresent()) {
            return byAbbreviation;
        }
        
        // If not found, try to find by name (case-insensitive)
        return facultyRepository.findByNameIgnoreCase(nameOrAbbreviation);
    }
}

