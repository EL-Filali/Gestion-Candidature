package  ma.donasid.recrute.controllers;

import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ADMIN")
public class AdminController {
    @Autowired
    AdminServices adminServices;
    @GetMapping("/users")
    ResponseEntity<?> getallUsers(){
        return adminServices.getAllUsers();
    }


    @GetMapping("/users/{id}")
    ResponseEntity<?> getUser(Long idUser){
        return adminServices.getUser(idUser);
    }

    @GetMapping
    ResponseEntity<?> getAllOffers(){
        return adminServices.getAllOffers();
    }

    @GetMapping("/offers/{id}")
    ResponseEntity<?> getOfferById(@PathVariable Long id){
        return adminServices.getOfferById(id);
    }

    @DeleteMapping("/offers/{id}")
    ResponseEntity<?> deleteOfferById(@PathVariable Long id){
        return adminServices.deleteOfferById(id);

    }

    @PutMapping("/offers/{id}/status")
    ResponseEntity<?> validerOffer(@PathVariable Long id){
        return adminServices.valideOffre(id);
    }
    @PutMapping("/users/{id}/role")
    ResponseEntity<?> changerRole(@PathVariable Long id,String role ){
        return adminServices.changeStatus(role,id);
    }

}
