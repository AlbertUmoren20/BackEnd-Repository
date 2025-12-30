package project.studentrepository.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Table(name = "circular")
@Entity
public class Circular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    @Column(name = "lecturer_email", nullable = false)
    private String lecturerEmail;
    
    private String title;
    private String description;
    private Integer year;
    
    @Lob
    @Column(name = "pdf_data", columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] pdfData;
    
    @Transient
    public String getFileUrl() {
        return "/lecturer/getFile/" + id;
    }
    
    @Transient
    public String getFilePath() {
        return "/lecturer/getFile/" + id;
    }
}

