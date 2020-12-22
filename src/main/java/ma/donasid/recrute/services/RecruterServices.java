package ma.donasid.recrute.services;

import ma.donasid.recrute.beans.Candidature;
import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.repositories.CandidatureRepository;
import ma.donasid.recrute.repositories.OfferRepository;
import ma.donasid.recrute.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@Service
public class RecruterServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    CandidatureRepository candidatureRepository;


    public ResponseEntity<?> createOffer(Offer offer,String name){
        User user =userRepository.findByUsername(name);
        offer.setOwner(user);
        offerRepository.save(offer);
        return new ResponseEntity<>(offer,HttpStatus.CREATED);
    }


    public ResponseEntity<?> validerCandidature(Long idCandidature)  {
        Optional<Candidature> optionalCandidature=candidatureRepository.findById(idCandidature);
        if(optionalCandidature.isPresent()){
            Candidature candidature =optionalCandidature.get();
            if((candidature.getStatus()=="VALIDEE")||(candidature.getStatus()=="ANNULEE")){

                return new ResponseEntity<>("Impossible de Valide une candidature " + candidature.getStatus(), HttpStatus.BAD_REQUEST);

            }else {

                candidature.setStatus("VALIDEE");
                candidatureRepository.save(candidature);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("Aucune Candidature avec cet ID",HttpStatus.BAD_REQUEST);

        }



    }


    public ResponseEntity<?> getAllRecruterOffers(String name){
        List<Offer> offers=userRepository.findByUsername(name).getOffers();
        for (Offer offer:offers) {
            if(offer.getStatus()=="EXPIRE")
                offers.remove(offer);
        }
        return new ResponseEntity<>(offers,HttpStatus.FOUND);
    }
    public ResponseEntity<?> getRecruterOffer(String name,Long idOffer){
        Offer offer2=null;
        List<Offer> offers=userRepository.findByUsername(name).getOffers();
        for (Offer offer:offers) {
            if((offer.getStatus()!="EXPIRE")||(offer.getId()==idOffer))
                offer2=offer;
        }
        if(offer2==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(offer2,HttpStatus.FOUND);
    }


    public ResponseEntity<?> getAllCandidatures(Long idOffer){
        List<Candidature> candidatures=offerRepository.findById(idOffer).get().getCandidatures();
        for (Candidature candidature:candidatures) {
            if((candidature.getStatus()=="EXPIREE")||(candidature.getStatus()=="ANNULEE"))
                candidatures.remove(candidature);

        }
        return new ResponseEntity<>(candidatures,HttpStatus.FOUND);
    }


    public ResponseEntity<?> rejeterCandidature(Long idCandidature)  {
        Optional<Candidature> optionalCandidature=candidatureRepository.findById(idCandidature);
        if(optionalCandidature.isPresent()){
            Candidature candidature =optionalCandidature.get();
            if((candidature.getStatus()=="REFUSEE")||(candidature.getStatus()=="ANNULEE")){

                return new ResponseEntity<>("Impossible de Valide une candidature " + candidature.getStatus(), HttpStatus.BAD_REQUEST);

            }else {

                candidature.setStatus("REFUSEE");
                candidatureRepository.save(candidature);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("Aucune Candidature avec cet ID",HttpStatus.BAD_REQUEST);

        }
    }


    public ResponseEntity<?> cloturerOffer(Long idOffer, String name) {
        Optional<Offer> optionalOffer=offerRepository.findById(idOffer);

        if (!optionalOffer.isPresent())
            return new ResponseEntity<>("Aucun Offre avec cet ID",HttpStatus.NOT_FOUND);
        else{
            Offer offer =optionalOffer.get();
            if (offer.getOwner().getUsername()!=name)
                return  new ResponseEntity<>("Aucun Offre de vos offre n a cet ID",HttpStatus.NOT_FOUND);
            else{
                List<Candidature> candidatures= offer.getCandidatures();
                for (Candidature candidature:candidatures) {
                    if((candidature.getStatus()!="VALIDEE")||((candidature.getStatus()!="ANNULEE"))) {
                        candidature.setStatus("REFUSEE");
                        candidatureRepository.save(candidature);
                    }

                }
                return new ResponseEntity<>(offer,HttpStatus.OK);
            }
        }

    }
}
