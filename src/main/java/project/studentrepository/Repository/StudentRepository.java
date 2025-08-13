package project.studentrepository.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer > {

    Student findByEmail(String email);
    Optional<Student> findOneByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
    boolean existsByMatricnumber(Integer matricnumber);

}
