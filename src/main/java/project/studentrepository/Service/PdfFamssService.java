package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.PdfFamss;
import project.studentrepository.Repository.PdfFamssRepository;

import java.util.List;

@Service
public class PdfFamssService {

    private final PdfFamssRepository pdfFamssRepository;

    @Autowired
    public PdfFamssService(PdfFamssRepository pdfFamssRepository) {
        this.pdfFamssRepository = pdfFamssRepository;
    }

    public List<PdfFamss> getUploadFamss() {
        return pdfFamssRepository.findAll();
    }

    public PdfFamss uploadPdfFile(PdfFamss pdfFamss) {
        return pdfFamssRepository.save(pdfFamss);
    }
}

