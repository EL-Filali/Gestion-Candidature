package ma.donasid.recrute.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CandidatServices {
    private static final String ACCESS_TOKEN ="USsB3oequVYAAAAAAAAAAa8LjbSu0ColuARJszzXU8jX0d3m0HL0QXCqJc6c7YZe" ;
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

    public  ResponseEntity<?> updateInfos(User user,String name ) {

            User usr=userRepository.findByEmail(name);
            if(user.getAdress()!="")
                usr.setAdress(user.getAdress());
            if(user.getFirstName()!="")
                usr.setFirstName(user.getFirstName());
            if(user.getLastName()!="")
                usr.setLastName(user.getLastName());
            if(user.getPhoneNumber()!="")
                usr.setPhoneNumber(user.getPhoneNumber());
            if(user.getPassword()!="")
                usr.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(usr);
            return new ResponseEntity<>(usr,HttpStatus.OK);


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


                    candidatureRepository.delete(candidature);


            }
        }
    }

    public Candidature getCandidature(Long idCandidature, String username) throws Exception {
        User user = userRepository.findByEmail(username);
        Candidature candidature=candidatureRepository.findByOwnerAndCode(user,idCandidature);
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
    public ResponseEntity<?> cloudUpload(String email, MultipartFile file,String type ) throws IOException, DbxException {
        User user =userRepository.findByEmail(email);
        switch(type){
            case "CV":
                user.setCvFileName(file.getOriginalFilename());
                break;
            case "PDP":
                user.setPdpFileName(file.getOriginalFilename());
                break;
            default:
                break;

        }
        try{

            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
            Metadata uploadMetaData = client.files().uploadBuilder("/"+user.getCin()+"/"+file.getOriginalFilename()).uploadAndFinish(inputStream);

            inputStream.close();
            userRepository.save(user);
            return new ResponseEntity<>("done",HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> cloudDownload( String email, String type ) throws IOException, DbxException {
        HttpHeaders headers = new HttpHeaders();
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        User user =userRepository.findByEmail(email);
        MultipartFile file = null;
        switch(type){
            case "CV":

                headers.setContentType(MediaType.APPLICATION_PDF);
                 file =new  MockMultipartFile(user.getCvFileName(), client.files().download("/"+user.getCin()+"/"+user.getCvFileName()).getInputStream());
                break;
            case "PDP":
                 file =new  MockMultipartFile( user.getPdpFileName(), client.files().download("/"+user.getCin()+"/"+user.getPdpFileName()).getInputStream());

                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            default:
                break;

        }
        return new ResponseEntity<>(file.getBytes(),headers,HttpStatus.OK);
    }


}
