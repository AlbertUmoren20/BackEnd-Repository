package project.studentrepository.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.studentrepository.Model.Circular;
import project.studentrepository.Model.Lecturer;
import project.studentrepository.Model.LoginDTO;
import project.studentrepository.Response.LoginResponse;
import project.studentrepository.Service.CircularService;
import project.studentrepository.Service.LecturerService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/lecturer")
public class LecturerController {

    private final LecturerService lecturerService;
    private final CircularService circularService;

    @Autowired
    public LecturerController(LecturerService lecturerService, CircularService circularService) {
        this.lecturerService = lecturerService;
        this.circularService = circularService;
    }

    @PostMapping("/add")
    public String add(@RequestBody Lecturer lecturer) {
        lecturerService.saveLecturer(lecturer);
        return "New Lecturer added";
    }

    @GetMapping("/getAll")
    public List<Lecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleLoginOptions() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginLecturer(@RequestBody LoginDTO loginDto) {
        LoginResponse loginResponse = lecturerService.loginLecturer(loginDto);
        
        // Return appropriate HTTP status codes based on login result
        if (loginResponse.getStatus()) {
            // Successful login
            return ResponseEntity.ok(loginResponse);
        } else {
            // Failed login - determine the appropriate status code
            String message = loginResponse.getMessage();
            if (message != null && message.equals("Password does not match")) {
                // Password mismatch - return 401 Unauthorized
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
            } else {
                // Email not found or other validation errors - return 400 Bad Request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginResponse);
            }
        }
    }

    @PostMapping("/uploadCircular")
    public ResponseEntity<Circular> uploadCircular(
            @RequestParam("file") MultipartFile file,
            @RequestParam("lecturerEmail") String lecturerEmail,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("year") Integer year) {
        
        Circular circular = new Circular();
        circular.setLecturerEmail(lecturerEmail);
        circular.setTitle(title);
        circular.setDescription(description);
        circular.setYear(year);

        try {
            circular.setPdfData(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        Circular savedCircular = circularService.uploadCircular(circular);
        return ResponseEntity.ok(savedCircular);
    }

    @GetMapping("/getCirculars")
    public ResponseEntity<List<Circular>> getCirculars(
            @RequestParam(required = false) String lecturerEmail) {
        
        List<Circular> circulars;
        if (lecturerEmail != null && !lecturerEmail.isEmpty()) {
            // Get circulars for a specific lecturer
            circulars = circularService.getCircularsByLecturerEmail(lecturerEmail);
        } else {
            // Get all circulars if no email is provided
            circulars = circularService.getAllCirculars();
        }
        
        return ResponseEntity.ok(circulars);
    }

    @GetMapping("/getFile/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Optional<Circular> circularOptional = circularService.findById(id);
        
        if (circularOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        Circular circular = circularOptional.get();
        byte[] pdfData = circular.getPdfData();
        
        if (pdfData == null || pdfData.length == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "circular_" + id + ".pdf");
        headers.setContentLength(pdfData.length);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfData);
    }
}

