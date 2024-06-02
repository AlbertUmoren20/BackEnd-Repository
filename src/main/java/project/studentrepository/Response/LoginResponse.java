package project.studentrepository.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    String message;
    Boolean status;

    public LoginResponse() {
    }

    public LoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
