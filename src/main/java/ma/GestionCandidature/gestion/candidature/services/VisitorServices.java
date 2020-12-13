package ma.GestionCandidature.gestion.candidature.services;

import ma.GestionCandidature.gestion.candidature.beans.Offer;
import ma.GestionCandidature.gestion.candidature.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class VisitorServices {
    @Autowired
    OfferRepository offerRepository;

    public List<Offer> getAllOffers(){
        return offerRepository.findAll();
    }



}
