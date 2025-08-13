package project.studentrepository.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.studentrepository.Model.*;
import project.studentrepository.Response.LoginResponse;
import project.studentrepository.Service.PdfFamssService;
import project.studentrepository.Service.PdfFbmasService;
import project.studentrepository.Service.ProjectService;
import project.studentrepository.Service.StudentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final ProjectService projectService;
    private final PdfFamssService pdfFamssService;
    private final PdfFbmasService pdfFbmasService;

    @Autowired
    public StudentController(StudentService studentService, ProjectService projectService,
                             PdfFamssService pdfFamssService, PdfFbmasService pdfFbmasService) {
        this.studentService = studentService;
        this.projectService = projectService;
        this.pdfFamssService = pdfFamssService;
        this.pdfFbmasService = pdfFbmasService;
    }

    @PostMapping("/add") // Adds data
    public String add(@RequestBody Student student) {
        studentService.saveStudent(student); // Go into student service section and make this operation
        return "New Student added";
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody StudentDto studentDto) {
        LoginResponse response = studentService.registerStudent(studentDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody LoginDTO logindto) {
        LoginResponse loginResponse = studentService.loginStudent(logindto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/famssProject")
    public ResponseEntity<FAMSSProject> addProject(@RequestBody FAMSSProject famssProject) {
        FAMSSProject famssProject1 = projectService.saveProject(famssProject);
        return ResponseEntity.ok(famssProject1);
    }

    @GetMapping("/getFamssProjects")
    public List<FAMSSProject> getAllProjectForFamss() {
        return projectService.getProjectsForFAMSS();
    }

    @GetMapping("/searchProject/byDepartment/{department}")
    public List<FAMSSProject> findByDepartment(@PathVariable String department) {
        return projectService.searchProjectsDept(department);
    }

    @GetMapping("/searchProject/byYear/{year}")
    public List<FAMSSProject> findByYear(@PathVariable Integer year) {
        return projectService.searchProjectYear(year);
    }

    @GetMapping("/searchProject/byTitle/{title}")
    public List<FAMSSProject> findByTitle(@PathVariable String title) {
        return projectService.searchProjectTitle(title);
    }

    @GetMapping("/searchProject/bySupervisor/{supervisor}")
    public List<FAMSSProject> findBySupervisor(@PathVariable String supervisor) {
        return projectService.searchProjectSupervior(supervisor);
    }


    @PostMapping("/uploadFamss")
    public ResponseEntity<PdfFamss> uploadPdfFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("title") String title,
                                                  @RequestParam("supervisor") String supervisor,
                                                  @RequestParam("projectBy") String projectBy,
                                                  @RequestParam("department") String department,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("year") Integer year) {
        PdfFamss pdfFamss = new PdfFamss();
        pdfFamss.setTitle(title);
        pdfFamss.setSupervisor(supervisor);
        pdfFamss.setProjectBy(projectBy);
        pdfFamss.setDepartment(department);
        pdfFamss.setDescription(description);
        pdfFamss.setYear(year);

        try {
            pdfFamss.setPdfData(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error
        }

        PdfFamss savedPdfFamss = pdfFamssService.uploadPdfFile(pdfFamss);
        return ResponseEntity.ok(savedPdfFamss);
    }

    @GetMapping("/getFamssUpload")
    public List<PdfFamss> getUploadFamss() {
        return pdfFamssService.getUploadFamss();
    }

//    FOR FBMAS FACULTY
@PostMapping("/uploadFbmas")
    public ResponseEntity<PdfFbmas> uploadPdfFiles(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("supervisor") String supervisor,
            @RequestParam("projectBy") String projectBy,
            @RequestParam("department") String department,
            @RequestParam("description") String description,
            @RequestParam("year") Integer year) {

        PdfFbmas pdfFbmas = new PdfFbmas();
        pdfFbmas.setTitle(title);
        pdfFbmas.setSupervisor(supervisor);
        pdfFbmas.setProjectBy(projectBy);
        pdfFbmas.setDepartment(department);
        pdfFbmas.setDescription(description);
        pdfFbmas.setYear(year);

        try {
            pdfFbmas.setPdfData(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error
        }
        PdfFbmas savedPdfFbmas = pdfFbmasService.getuploadPdfFiles(pdfFbmas);
        return ResponseEntity.ok(savedPdfFbmas);
    }
    @GetMapping("/getFbmasUpload")
    public List<PdfFbmas> getUploadFbmas() {
        return pdfFbmasService.getUploadFbmas();
    }
    @GetMapping("/getFbmasUpload/{id}")
    public ResponseEntity<Optional<PdfFbmas>> findById(@PathVariable Long id) {
        Optional<PdfFbmas> pdfFbmas = pdfFbmasService.findById(id);
        if (pdfFbmas.isPresent()) {
            return ResponseEntity.ok(pdfFbmas);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
        }
    }

    }