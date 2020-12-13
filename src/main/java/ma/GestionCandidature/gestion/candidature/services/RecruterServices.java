package ma.GestionCandidature.gestion.candidature.services;

import ma.GestionCandidature.gestion.candidature.beans.Offer;
import ma.GestionCandidature.gestion.candidature.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruterServices {
    @Autowired
    OfferRepository offerRepository;

    public void createOffer(Offer offer){
        offerRepository.save(offer);
    }
}
