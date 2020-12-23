package ma.donasid.recrute.responses;

import javax.validation.constraints.NotBlank;

public class ConnexionResponse {
    @NotBlank(message = "Ne peux pas etre vide")
    private String email;
    @NotBlank(message = "Ne peux pas etre vide")
    private String password ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
