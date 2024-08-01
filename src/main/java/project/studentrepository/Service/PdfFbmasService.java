package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.PdfFbmas;
import project.studentrepository.Repository.PdfFbmasRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PdfFbmasService {

    private final PdfFbmasRepository pdfFbmasRepository;


    @Autowired
    public PdfFbmasService(PdfFbmasRepository pdfFbmasRepository) {
        this.pdfFbmasRepository = pdfFbmasRepository;
    }
//Not sure here
    public Optional<PdfFbmas> findById(Long id) {
        return pdfFbmasRepository.findById(id);
    }

    public List<PdfFbmas> getUploadFbmas() {
        return pdfFbmasRepository.findAll();
    }
    public PdfFbmas getuploadPdfFiles(PdfFbmas pdfFbmas) {
        return pdfFbmasRepository.save(pdfFbmas);
    }


}
