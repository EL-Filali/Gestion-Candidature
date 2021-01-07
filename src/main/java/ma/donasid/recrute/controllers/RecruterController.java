package ma.donasid.recrute.controllers;

import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.services.AdminServices;
import ma.donasid.recrute.services.CandidatServices;
import ma.donasid.recrute.services.RecruterServices;
import ma.donasid.recrute.services.ValidationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/RECRUTEUR")
public class RecruterController {
    @Autowired
    ValidationServices validationServices;
    @Autowired
    RecruterServices recruterServices;
    @PostMapping("/offers")
    ResponseEntity<?> addOffer(@Valid @RequestBody Offer offer , BindingResult result,Principal principal){
        if(result.hasErrors()) {


            return validationServices.MapValidationService(result);
        }else {
            return  recruterServices.createOffer(offer, principal.getName());

        }
    }
    @PutMapping("Offers/{idOffre}/candidatures/{id}/status")
    ResponseEntity<?> changerStatusCandidature(@PathVariable Long id,Long idOffre, String status,Principal principal) {

        switch (status) {
            case "VALIDEE":
                return recruterServices.validerCandidature(id,idOffre,principal.getName());


            case "REFUSEE":
                return recruterServices.rejeterCandidature(id,idOffre,principal.getName());


            default:

                return new ResponseEntity<>("LE Status doit  VALIDEE ou REFUSEE", HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/offers/{id}")
    ResponseEntity<?>   getOffers(@PathVariable Long id,  Principal principal){
        String name=principal.getName();
        return recruterServices.getRecruterOffer(name, id);

    }
    @PutMapping("/offers/{id}/status")
    ResponseEntity<?> cloturerOffer(Long idOffer,Principal principal){
        return recruterServices.cloturerOffer(idOffer,principal.getName());
    }
    @GetMapping("offers/{idOffer}/candidatures/{idCandidature}")
    ResponseEntity<?> getCandidature(Long idOffer,Long idCandidature,Principal principal){
        return recruterServices.getCandidature(idOffer,principal.getName(),idCandidature);
    }
    @GetMapping("offers/{idOffer}/candidatures/{idCandidature}/owner")
    ResponseEntity<?> getOwner(Long idOffer,Long idCandidature,Principal principal){
        return recruterServices.getOwnerCandidature(idOffer,idCandidature,principal.getName());
    }
    @GetMapping("offers/{idOffer}/candidatures/{idCandidature}/owner/pdp")
    ResponseEntity<?> getOwnerPdp(Long idOffer,Long idCandidature,Principal principal){
        return recruterServices.getpdpOwnerCandidature(idOffer,idCandidature,principal.getName());
    }
    @GetMapping("offers/{idOffer}/candidatures/{idCandidature}/owner/cv")
    ResponseEntity<?> getOwnerCv(Long idOffer,Long idCandidature,Principal principal){
        return recruterServices.getCvOwnerCandidature(idOffer,idCandidature,principal.getName());
    }

}
