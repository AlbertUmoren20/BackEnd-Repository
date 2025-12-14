package project.studentrepository.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStatisticsDTO {
    private String faculty;
    private Integer year;
    private Integer count;
    private String projectType; // "FAMSS", "FBMAS", "PdfFamss", "PdfFbmas"
}

