package project.studentrepository.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.Student;
import project.studentrepository.Model.StudentDto;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer > {

    Student findByEmail(String email);
    Optional<Student> findOneByEmailAndPassword(String email, String password);  //A method created
    // to find by email and by password

    Student save(StudentDto studentDto);

}
