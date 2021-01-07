package ma.donasid.recrute.controllers;

import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Candidature;
import ma.donasid.recrute.services.CandidatServices;
import ma.donasid.recrute.services.ValidationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/CANDIDAT")
public class CandidatController {
  @Autowired
    CandidatServices candidatServices;

   @GetMapping("/candidatures")
    ResponseEntity<?> getAll(Principal principal) {
        try{List<Candidature> candidatures=candidatServices.getCandidatures(principal.getName());
            return new ResponseEntity<>(candidatures, HttpStatus.FOUND);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }


    }
    @GetMapping("/candidatures/{id}")
    ResponseEntity<?> getCandidature(@PathVariable  Long idCandidature, Principal principal) throws Exception {
        try{
            Candidature candidature=candidatServices.getCandidature(idCandidature,principal.getName());
            return new ResponseEntity<>(candidature,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }



    }


    @GetMapping("/")
    ResponseEntity<?> getUser(Principal principal) {
         User user =candidatServices.getInfos(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @PostMapping
    ResponseEntity<?> annulerCandidature(@PathVariable Long id ,Principal principal) throws Exception {
        try{
            candidatServices.annulerCandidature(id,principal.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

        }

    }
    @PostMapping("/offers/{id}")
    ResponseEntity<?> postuler(@PathVariable @RequestBody Long id ,Principal principal, Candidature candidature) throws Exception {
        try {
            candidatServices.postuler(id,principal.getName(),candidature);
            return new ResponseEntity<>(candidature,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(value = "/cv", method = RequestMethod.POST)
    ResponseEntity<?> uploadCV(@RequestParam("file")MultipartFile file, Principal principal) throws IOException {
       return candidatServices.uploadFile(file,principal.getName(),"CV");

    }
    @RequestMapping(value = "/cv", method = RequestMethod.GET)
    ResponseEntity<?> downloadCV( Principal principal) throws IOException {
        return candidatServices.downloadFile(principal.getName(),"CV");

    }
    @RequestMapping(value = "/pdp", method = RequestMethod.POST,produces={"image/jpeg"})
    ResponseEntity<?> uploadPDP(@RequestParam("file")MultipartFile file, Principal principal) throws IOException {
        return candidatServices.uploadFile(file,principal.getName(),"PDP");

    }
    @RequestMapping(value = "/pdp", method = RequestMethod.GET)
    ResponseEntity<?> downloadPDP( Principal principal) throws IOException {
        return candidatServices.downloadFile(principal.getName(),"PDP");

    }

    @PutMapping
    public ResponseEntity<?> updateInfos(@Valid @RequestBody User user, Principal principal, BindingResult result){

       return candidatServices.updateInfos(user,result);
    }


}
