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



}
