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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorServices {
    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<?>  getAllOffers(){
        List <Offer> offers=offerRepository.findByStatus("VALIDE");

        return new ResponseEntity<>(offers,HttpStatus.OK);
    }

    public ResponseEntity<?> userRegister(User user) throws IOException {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Path path = Paths.get("./uploads/"+user.getCin()+"/");
        Files.createDirectory(path);
         path = Paths.get("./uploads/"+user.getCin()+"/PDP/");
        Files.createDirectory(path);
        path = Paths.get("./uploads/"+user.getCin()+"/CV/");
        Files.createDirectory(path);
        return  new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    public ResponseEntity<?> getOffer(Long id)  {
        Optional<Offer> offer=offerRepository.findById(id);
        if(offer.isPresent()){
            return  new ResponseEntity<>(offer.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Aucun Offre avec cet Id", HttpStatus.NOT_FOUND);
        }

    }




}
