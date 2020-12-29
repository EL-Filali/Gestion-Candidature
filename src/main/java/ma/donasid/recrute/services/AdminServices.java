package ma.donasid.recrute.services;

import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.repositories.UserRepository;
import ma.donasid.recrute.repositories.CandidatureRepository;
import ma.donasid.recrute.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    CandidatureRepository candidatureRepository;


   public ResponseEntity<?> getAllUsers(){
       return new ResponseEntity<>(userRepository.findAll(),HttpStatus.FOUND);

    }
    public ResponseEntity<?> getUser(Long idUser){
       Optional<User> optionalUser =userRepository.findById(idUser);
       if(optionalUser.isPresent()){
            User user =optionalUser.get();
            return new ResponseEntity<>(user, HttpStatus.FOUND);
       }else{
           return new ResponseEntity<>( "Aucun utilisateur avec cet ID",HttpStatus.NOT_FOUND);
       }
    }

    public ResponseEntity<?> getAllOffers(){
        return new ResponseEntity<>(offerRepository.findAll(),HttpStatus.FOUND);
    }

    private ResponseEntity<?> rendreAdmin(Long idUser){
        Optional<User> optionalUser=userRepository.findById(idUser);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("ADMIN");
            userRepository.save(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else
            return new ResponseEntity<>("Aucun User avec cet ID",HttpStatus.NOT_FOUND);




    }
    private ResponseEntity<?> rendreRecruteur(Long idUser){
        Optional<User> optionalUser=userRepository.findById(idUser);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("RECRUTEUR");
            userRepository.save(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else
            return new ResponseEntity<>("Aucun User avec cet ID",HttpStatus.NOT_FOUND);

    }
    private ResponseEntity<?> rendreCandidat(Long idUser){
        Optional<User> optionalUser=userRepository.findById(idUser);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("CANDIDAT");
            userRepository.save(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else
            return new ResponseEntity<>("Aucun User avec cet ID",HttpStatus.NOT_FOUND);

    }
    public  ResponseEntity<?> valideOffre(Long idOffre){
        Optional<Offer> optionalOffer=offerRepository.findById(idOffre);
        if (optionalOffer.isPresent()){
            Offer offer = optionalOffer.get();
            offer.setStatus("VALIDE");
            offerRepository.save(offer);
            return new ResponseEntity<>(offer,HttpStatus.OK);
        }else
            return new ResponseEntity<>("Aucun Offre avec cet ID",HttpStatus.NOT_FOUND);
    }
    public  ResponseEntity<?> deleteOfferById(Long idOffer){
       offerRepository.deleteById(idOffer);
       return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> changeStatus(String status,Long idCandidat)   {
       status=status.toUpperCase();
       switch (status) {
           case "ADMIN":
               return rendreAdmin(idCandidat);

           case "RECRUTEUR":
               return rendreRecruteur(idCandidat);

           case "CANDIDAT":
              return rendreCandidat(idCandidat);

           default:
               return new ResponseEntity<>("Status inexistant (ADMIN RECRUTEUR CANDIDAT",HttpStatus.BAD_REQUEST);
       }
    }
    public ResponseEntity<?> getOfferById(Long idOffer){
       Optional<Offer> optionalOffer =offerRepository.findById(idOffer);
       if(optionalOffer.isPresent()){
           return new ResponseEntity<>(optionalOffer.get(),HttpStatus.FOUND);
       }else
           return new ResponseEntity<>("Aucun offer avec cet Id",HttpStatus.NOT_FOUND);
    }

}