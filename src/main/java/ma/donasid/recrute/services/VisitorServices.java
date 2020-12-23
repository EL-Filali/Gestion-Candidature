package ma.donasid.recrute.services;

import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.repositories.OfferRepository;
import ma.donasid.recrute.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorServices {
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<?>  getAllOffers(){
        List <Offer> offers=offerRepository.findAll();
        for (Offer offer:offers
        ) {
            offer.setOwner(null);

        }
        return new ResponseEntity<>(offers,HttpStatus.FOUND);
    }

    public ResponseEntity<?> userRegister(User user)  {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return  new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    public ResponseEntity<?> getOffer(Long id)  {
        Optional<Offer> offer=offerRepository.findById(id);
        if(offer.isPresent()){
            return  new ResponseEntity<>(offer.get(),HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("Aucun Offre avec cet Id", HttpStatus.NOT_FOUND);
        }

    }




}
