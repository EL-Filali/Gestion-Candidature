package ma.donasid.recrute.controllers;

import ma.donasid.recrute.beans.Offer;
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
@RequestMapping("api/recruter")
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
    @PutMapping("/candidatures/{id}/status")
    ResponseEntity<?> changerStatusCandidature(@PathVariable Long id, String status) {

        switch (status) {
            case "VALIDEE":
                return recruterServices.validerCandidature(id);


            case "REFUSEE":
                return recruterServices.rejeterCandidature(id);


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

}
