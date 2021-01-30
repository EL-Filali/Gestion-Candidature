package ma.donasid.recrute.services;

import io.jsonwebtoken.Header;
import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Candidature;
import ma.donasid.recrute.config.JwtTokenProvider;
import ma.donasid.recrute.exceptions.IllegalOperationException;
import ma.donasid.recrute.repositories.UserRepository;
import ma.donasid.recrute.repositories.CandidatureRepository;
import ma.donasid.recrute.repositories.OfferRepository;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired
    ValidationServices validationServices;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public  ResponseEntity<?> updateInfos(User user, BindingResult result,String name ) {
        if(result.hasErrors()){
            return new ResponseEntity<>(validationServices.MapValidationService(result),HttpStatus.BAD_REQUEST);
        }else{

            User usr=userRepository.findByEmail(name);
            usr.setAdress(user.getAdress());
            usr.setCin(user.getCin());
            usr.setFirstName(user.getFirstName());
            usr.setLastName(user.getLastName());
            usr.setPhoneNumber(user.getPhoneNumber());
            usr.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(usr);
            return new ResponseEntity<>(usr,HttpStatus.CREATED);
        }

    }

    public List<Candidature> getCandidatures(String name)  {
        User user = userRepository.findByEmail(name);
        List<Candidature> candidatures= user.getCandidatures();

        return candidatures;
    }
    public ResponseEntity<?> postuler(Long idOffer,String name,Candidature candidature){

            Offer offer =offerRepository.findById(idOffer).get();
            if(offer.getStatus().equals("VALIDE")){
                System.out.print("valider");
                User user=userRepository.findByEmail(name);
                candidature.setTheOffer(offer);
                candidature.setOwner(user);

                candidatureRepository.save(candidature);
                return new ResponseEntity<>(candidature,HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }



    }

    public void annulerCandidature(Long idCandidature,String username) throws Exception {
        User user = userRepository.findByEmail(username);
        Candidature candidature = candidatureRepository.findByOwnerAndCode(user,idCandidature);
        if(candidature==null)
            throw new Exception("Aucune Candidature avec cet ID");
        else {

            
            if ((candidature.getStatus() == "ACCEPTEE") || (candidature.getStatus() == "ANNULEE")) {
                throw new IllegalOperationException("Candidature ACCEPTEE ou REFUSEE ne peux pas etre ANNULEE");
            } else {
                if(candidature.getOwner().equals(user)){
                    candidature.setStatus("ANNULEE");
                    candidature.setTheOffer(null);
                    candidatureRepository.save(candidature);
                }else{
                    throw new Exception("Aucun candidature avec cet ID pour ce user");
                }

            }
        }
    }

    public Candidature getCandidature(Long idCandidature, String username) throws Exception {
        User user = userRepository.findByEmail(username);
        List<Candidature> candidatures= user.getCandidatures();
        Candidature candidature = null;
        for (Candidature c:candidatures
             ) {
            if(c.getCode()==idCandidature)
                 candidature=c;

        }
        if (candidature==null)
            throw new Exception("Aucun candidature avec cet ID pour ce user");
        else
            return candidature;
    }
    public User getInfos(String name){
        User user = userRepository.findByEmail(name);
        user.setPassword(null);

        return user;
    }
    public ResponseEntity<?> uploadFile(MultipartFile file, String email,String fileTypeName) throws IOException {
        User user=userRepository.findByEmail(email);
        String regex = null;
        switch(fileTypeName){
            case "CV":
                user.setCvFileName(file.getOriginalFilename());
                regex=".*[.]pdf";
                break;
            case "PDP":
                user.setPdpFileName(file.getOriginalFilename());
                regex=".*[.]jpeg";
                break;
            default:
                break;

        }
        System.out.println(file.getOriginalFilename());
        if(file.getOriginalFilename().matches(regex)){
            userRepository.save(user);
            Path fileNameAndPath = Paths.get("./uploads/",user.getCin(),"/",fileTypeName,"/",file.getOriginalFilename());
            try{
                Files.write(fileNameAndPath,file.getBytes());

                return new ResponseEntity<>("Saved",HttpStatus.OK);
            }catch(Exception exception){
                return new ResponseEntity<>("Erreur lors de upload : "+exception.getMessage(),HttpStatus.EXPECTATION_FAILED);
            }
        }else{
            return new ResponseEntity<>("Format de fichier non valid ",HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<?> downloadFile( String email,String fileTypeName) throws IOException {
        User user=userRepository.findByEmail(email);
        HttpHeaders headers = new HttpHeaders();

        String fileName = null;
        switch(fileTypeName){
            case "CV":
                fileName= user.getCvFileName();

                headers.setContentType(MediaType.APPLICATION_PDF);
                break;
            case "PDP":

                fileName=user.getPdpFileName();
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            default:
                break;

        }
        System.out.println(fileName);

        Path fileNameAndPath = Paths.get("./uploads/",user.getCin(),"/",fileTypeName,"/",fileName);
        try{
            MultipartFile file=new MockMultipartFile(fileName, Files.readAllBytes(fileNameAndPath));
            return new ResponseEntity<>(file.getBytes(),headers,HttpStatus.OK);
        }catch(Exception exception){
            return new ResponseEntity<>("Erreur lors de upload : "+exception.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }

}
