package  ma.donasid.recrute.controllers;

import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    ResponseEntity<?> getUser(@PathVariable Long idUser){
        return adminServices.getUser(idUser);
    }

    @GetMapping("/offers")
    ResponseEntity<?> getAllOffers(){
        return adminServices.getAllOffers();
    }

    @GetMapping("/offers/{id}")
    ResponseEntity<?> getOfferById(@PathVariable Long id){
        return adminServices.getOfferById(id);
    }

    @DeleteMapping("/offers/{id}")
    ResponseEntity<?> deleteOfferById(@PathVariable Long id , Principal principal){
        return adminServices.deleteOfferById(id);

    }

    @PutMapping("/offers/{id}/status")
    ResponseEntity<?> validerOffer(@PathVariable Long id, Principal principal){
        return adminServices.valideOffre(id);
    }
    @PutMapping("/users/{id}/role")
    ResponseEntity<?> changerRole(@PathVariable Long id,@RequestBody User user , Principal principal ){
        return adminServices.changeStatus(user.getRole(),id);
    }
    @DeleteMapping("/users/{id}")
    ResponseEntity<?> suspendUser(@PathVariable Long id, Principal principal){
        return adminServices.suspendUser(id);
    }
}
