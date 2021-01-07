package ma.donasid.recrute.responses;

import ma.donasid.recrute.beans.User;

import java.util.HashMap;

public class ConnexionResponse {
    private String jwt;
    private User user;

    public ConnexionResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("jwt",jwt);
        map.put("user",user );
        return map;
    }
}
