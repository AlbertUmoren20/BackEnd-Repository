package project.studentrepository.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.Circular;

import java.util.List;

@Repository
public interface CircularRepository extends JpaRepository<Circular, Long> {
    List<Circular> findByLecturerEmail(String lecturerEmail);
}

