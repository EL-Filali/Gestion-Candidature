package ma.donasid.recrute.services;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import ma.donasid.recrute.beans.Candidature;
import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.repositories.CandidatureRepository;
import ma.donasid.recrute.repositories.OfferRepository;
import ma.donasid.recrute.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import javax.xml.ws.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    String ACCESS_TOKEN ="USsB3oequVYAAAAAAAAAAa8LjbSu0ColuARJszzXU8jX0d3m0HL0QXCqJc6c7YZe";

    public ResponseEntity<?> createOffer(Offer offer,String name){
        User user =userRepository.findByEmail(name);
        offer.setOwner(user);
        offerRepository.save(offer);
        return new ResponseEntity<>(offer,HttpStatus.CREATED);
    }


    public ResponseEntity<?> validerCandidature(Long idCandidature,Long idOffre , String name)  {
        ResponseEntity<?> response = getCandidature(idOffre,name,idCandidature);
        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            return response;
        }else{
            Candidature candidature = (Candidature) response.getBody();
            if((candidature.getStatus()=="VALIDEE")||(candidature.getStatus()=="ANNULEE")){

                return new ResponseEntity<>("Impossible de Valide une candidature " + candidature.getStatus(), HttpStatus.BAD_REQUEST);

            }else {

                candidature.setStatus("VALIDEE");
                candidatureRepository.save(candidature);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }



    }


    public ResponseEntity<?> getAllRecruterOffers(String name){
        List<Offer> offers=userRepository.findByEmail(name).getOffers();
        for (Offer offer:offers) {
            if(offer.getStatus()=="EXPIRE")
                offers.remove(offer);
        }
        return new ResponseEntity<>(offers,HttpStatus.OK);
    }
    public ResponseEntity<?> getRecruterOffer(String name,Long idOffer){
        Offer offer2=null;
        List<Offer> offers=userRepository.findByEmail(name).getOffers();
        for (Offer offer:offers) {
            if((offer.getStatus()!="EXPIRE")||(offer.getId()==idOffer))
                offer2=offer;
        }
        if(offer2==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(offer2,HttpStatus.OK);
    }





    public ResponseEntity<?> rejeterCandidature(Long idOffre, Long idCandidature, String name)  {

            User user=userRepository.findByEmail(name);
        Offer offer = offerRepository.findByOwnerAndId(user,idOffre);
            Candidature candidature = candidatureRepository.findByCodeAndTheOffer( idCandidature,offer);
            if(candidature==null) {
                return new ResponseEntity<>("Aucune candidature", HttpStatus.BAD_REQUEST);
            }
            else{
                candidature.setStatus("REFUSEE");
                candidatureRepository.save(candidature);
                return new ResponseEntity<>(HttpStatus.OK);
            }



    }

    @Transactional
    public ResponseEntity<?> cloturerOffer(Long idOffer, String name) {
       Offer offer= offerRepository.findByOwnerAndId(userRepository.findByEmail(name),idOffer);

        if (offer==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else{



                candidatureRepository.updateCandidatureAfterOfferExpired(offer);
                offer.setStatus("EXPIRE");
                offerRepository.save(offer);
                return new ResponseEntity<>(offer,HttpStatus.OK);

        }

    }


    public ResponseEntity<?> getCandidatures(Long idOffre,String name){
        Optional<Offer> offer=offerRepository.findById(idOffre);
        if(offer.isPresent()) {
            List<Candidature> candidatures = candidatureRepository.findByTheOffer(offer.get());
            return new ResponseEntity<>(candidatures, HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<?> getCandidature(Long idOffre,String email,Long idCandidature){
        Offer offer = offerRepository.findById(idOffre).get();
        Candidature candidature = candidatureRepository.findByTheOfferAndCode(offer,idCandidature);
        if(candidature==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(candidature,HttpStatus.OK);
        }
    }


    public ResponseEntity<?> getOwnerCandidature(Long idOffer, Long idCandidature, String name) {
        ResponseEntity response =getCandidature(idOffer,name,idCandidature);
        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return response ;
        else{
            Candidature candidature = (Candidature) response.getBody();
            User user=candidature.getOwner();
            user.setPassword(null);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }

    }
    public ResponseEntity<?> getCvOwnerCandidature(Long idOffer, Long idCandidature, String name) throws DbxException, IOException {
        try{
        User recruteur=userRepository.findByEmail(name);
        HttpHeaders headers = new HttpHeaders();
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        Offer offer= offerRepository.findByOwnerAndId(recruteur,idOffer);
        Candidature candidature =candidatureRepository.findByCodeAndTheOffer(idCandidature,offer);

        User user =candidature.getOwner();
        MultipartFile file = null;

        headers.setContentType(MediaType.APPLICATION_PDF);
                file =new  MockMultipartFile(user.getCvFileName(), client.files().download("/"+user.getCin()+"/"+user.getCvFileName()).getInputStream());

        return new ResponseEntity<>(file.getBytes(),headers,HttpStatus.OK);}
        catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }

    }


    /*public ResponseEntity<?> getpdpOwnerCandidature(Long idOffer, Long idCandidature, String name){
        ResponseEntity  response=getOwnerCandidature(idOffer,idCandidature,name);
        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return response;
        else{
            User user = (User) response.getBody();
            HttpHeaders headers = new HttpHeaders();

            String fileName= user.getPdpFileName();
            headers.setContentType(MediaType.IMAGE_JPEG);
            System.out.println(fileName);

            Path fileNameAndPath = Paths.get("./uploads/",user.getCin(),"/PDP/",fileName);
            try{
                MultipartFile file=new MockMultipartFile(fileName, Files.readAllBytes(fileNameAndPath));
                return new ResponseEntity<>(file.getBytes(),headers,HttpStatus.OK);
            }catch(Exception exception){
                return new ResponseEntity<>("Erreur lors du download : "+exception.getMessage(),HttpStatus.EXPECTATION_FAILED);
            }
        }
    }*/


}
