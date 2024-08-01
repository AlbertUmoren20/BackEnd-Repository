package project.studentrepository.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.studentrepository.Model.PdfFbmas;

import java.util.List;


@Repository
public interface PdfFbmasRepository extends JpaRepository<PdfFbmas,Long> {

}
