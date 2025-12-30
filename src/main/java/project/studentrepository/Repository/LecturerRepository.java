package project.studentrepository.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.Lecturer;

import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
    Lecturer findByEmail(String email);
    Optional<Lecturer> findOneByEmailAndPassword(String email, String password);
}

