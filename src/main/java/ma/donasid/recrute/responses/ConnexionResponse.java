package ma.donasid.recrute.responses;

import javax.validation.constraints.NotBlank;

public class ConnexionResponse {
    @NotBlank(message = "Ne peux pas etre vide")
    private String username;
    @NotBlank(message = "Ne peux pas etre vide")
    private String password ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
