package project.studentrepository.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.studentrepository.Model.Circular;
import project.studentrepository.Repository.CircularRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CircularService {

    private final CircularRepository circularRepository;

    @Autowired
    public CircularService(CircularRepository circularRepository) {
        this.circularRepository = circularRepository;
    }

    public Circular uploadCircular(Circular circular) {
        return circularRepository.save(circular);
    }

    public List<Circular> getCircularsByLecturerEmail(String lecturerEmail) {
        return circularRepository.findByLecturerEmail(lecturerEmail);
    }

    public List<Circular> getAllCirculars() {
        return circularRepository.findAll();
    }

    public Optional<Circular> findById(Long id) {
        return circularRepository.findById(id);
    }
}

