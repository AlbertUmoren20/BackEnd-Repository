package project.studentrepository.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    String message;
    Boolean status;
    Integer userLevel;
    String userFullname;
    String userRole;
    String userEmail;

    public LoginResponse() {
    }

    public LoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public LoginResponse(String message, Boolean status, Integer userLevel, String userFullname, String userRole, String userEmail) {
        this.message = message;
        this.status = status;
        this.userLevel = userLevel;
        this.userFullname = userFullname;
        this.userRole = userRole;
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", userLevel=" + userLevel +
                ", userFullname='" + userFullname + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
