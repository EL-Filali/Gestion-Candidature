package ma.GestionCandidature.gestion.candidature.controllers;

import ma.GestionCandidature.gestion.candidature.beans.User;
import ma.GestionCandidature.gestion.candidature.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    AdminServices adminServices;
    @GetMapping("/users")
    List<User> getall(){
        List<User> users = new ArrayList<User>();
        users=adminServices.getAllUsers();
        return users;
    }
    @GetMapping("/u")
    void aaaa(){
        User users = new User("JK","a","a","a","a","a","a",null,null,null,null,null,null);
        adminServices.save(users);

    }
    //User(String CIN, String email, String username, String password, String firstName, String lastName, String adress, String phoneNumber, String role, Boolean enabled, Boolean accountExpired, Date createdAt, Date uploadedAt)

}
