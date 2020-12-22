package ma.donasid.recrute.services;

import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Candidature;
import ma.donasid.recrute.config.JwtTokenProvider;
import ma.donasid.recrute.exceptions.IllegalOperationException;
import ma.donasid.recrute.repositories.UserRepository;
import ma.donasid.recrute.repositories.CandidatureRepository;
import ma.donasid.recrute.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatServices {
    @Autowired
    CandidatureRepository candidatureRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OfferRepository offerRepository;

    public List<Candidature> getCandidatures(String name)  {
        User user = userRepository.findByUsername(name);
        List<Candidature> candidatures= user.getCandidatures();

        return candidatures;
    }
    public void postuler(Long idOffer,String name,Candidature candidature){


        candidature.setOffer(offerRepository.findById(idOffer).get());
        candidature.setOwner(userRepository.findByUsername(name));
        candidatureRepository.save(candidature);
    }

    public void annulerCandidature(Long idCandidature,String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if(candidatureRepository.findById(idCandidature).isPresent())
            throw new Exception("Aucune Candidature avec cet ID");
        else {

            Candidature candidature = candidatureRepository.findById(idCandidature).get();
            if ((candidature.getStatus() == "ACCEPTEE") || (candidature.getStatus() == "ANNULEE")) {
                throw new IllegalOperationException("Candidature ACCEPTEE ou REFUSEE ne peux pas etre ANNULEE");
            } else {
                if(candidature.getOwner().equals(user)){
                    candidature.setStatus("ANNULEE");
                    candidature.setOffer(null);
                    candidatureRepository.save(candidature);
                }else{
                    throw new Exception("Aucun candidature avec cet ID pour ce user");
                }

            }
        }
    }

    public Candidature getCandidature(Long idCandidature, String username) throws Exception {
        User user = userRepository.findByUsername(username);
        List<Candidature> candidatures= user.getCandidatures();
        Candidature candidature = null;
        for (Candidature c:candidatures
             ) {
            if(c.getId()==idCandidature)
                 candidature=c;

        }
        if (candidature==null)
            throw new Exception("Aucun candidature avec cet ID pour ce user");
        else
            return candidature;
    }
    public User getInfos(String name){
        User user = userRepository.findByUsername(name);
        user.setPassword(null);

        return user;
    }
    
}
