package ma.GestionCandidature.gestion.candidature.controllers;

import ma.GestionCandidature.gestion.candidature.beans.Offer;
import ma.GestionCandidature.gestion.candidature.repositories.OfferRepository;
import ma.GestionCandidature.gestion.candidature.services.RecruterServices;
import ma.GestionCandidature.gestion.candidature.services.VisitorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VisitorController {
    @Autowired
    VisitorServices visitorServices;
    @Autowired
    RecruterServices recruterServices;
    @GetMapping("/p")
    Offer get(){Offer offre= new Offer();
    offre.setTitle("java dev");
    List<String> keys=new ArrayList<String>();
    keys.add("java");
    keys.add("Javascript");
    keys.add("spring");
    keys.add("html");
    offre.setkWs(keys);
    keys.clear();
    keys.add("Question 1");
    keys.add("Question 2");
    keys.add("Question 3");
    keys.add("Question 4");
    offre.setQuestions(keys);

        return  offre ;
    }
    @GetMapping("/offers")
    List<Offer> getAllOffers(){

        return visitorServices.getAllOffers() ;
    }
    @PostMapping("/admin/offers")
    ResponseEntity<?>  addOffer(@Valid @RequestBody Offer offer , BindingResult result){
        if(result.hasErrors()) {
            Map<String,String> errorMap = new HashMap<>();
            for (FieldError er: result.getFieldErrors())
                errorMap.put(er.getField(),er.getDefaultMessage());

            return new ResponseEntity <Map> ( errorMap  , HttpStatus.BAD_REQUEST) ;
        }else {
            recruterServices.createOffer(offer);
            return new ResponseEntity <Offer> ( offer , HttpStatus.CREATED) ;
        }
    }
}
