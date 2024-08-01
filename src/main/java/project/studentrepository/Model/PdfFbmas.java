package project.studentrepository.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
@Table (name = "pdfbmas")
@Entity
public class PdfFbmas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;
    private String title;
    private String supervisor;
    @Column(name = "project_by")
    private String projectBy;
    private String department;
    private String description;
    private Integer year;
    @Lob
    @Column(name = "pdf_data", columnDefinition = "LONGBLOB")
    private byte[] pdfData;
}

