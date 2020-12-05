package ma.GestionCandidature.gestion.candidature.responses;

public class CandidatJPAExceptionResponse {
    private String message;

    public CandidatJPAExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
