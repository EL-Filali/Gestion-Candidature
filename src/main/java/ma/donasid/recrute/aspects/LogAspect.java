package ma.donasid.recrute.aspects;

import ma.donasid.recrute.beans.Candidature;
import ma.donasid.recrute.beans.Log;
import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.repositories.LogRepository;
import ma.donasid.recrute.repositories.UserRepository;
import ma.donasid.recrute.responses.ConnexionRequest;
import ma.donasid.recrute.responses.ConnexionResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Locale;

@Aspect
@Component
public class LogAspect {
    @Autowired
    LogRepository logRepository;
    @Autowired
    UserRepository userRepository;
    @AfterReturning(pointcut="execution(* ma.donasid.recrute.controllers.VisitorController.connexion(..))",returning = "result")
    void saveConnexionLog(JoinPoint join, ResponseEntity<?> result){

        ConnexionRequest connexionRequest=null;
        Object[] args =join.getArgs();
        for (Object obj:args) {
            if(obj instanceof ConnexionRequest)
                connexionRequest = (ConnexionRequest) obj;
        }
        if(connexionRequest!= null) {
            if((result.getStatusCodeValue()==200)||(result.getStatusCodeValue()==201)){
               String action= " Connexion user email '" +connexionRequest.getEmail()+"' Reussie ";
                Log log = new Log();
                log.setAction(action.toUpperCase());
                log.setUser(userRepository.findByEmail(connexionRequest.getEmail()));
                logRepository.save(log);
            }else{
                String action= " Connexion user email '" +connexionRequest.getEmail()+"' Echoue ";

                Log log = new Log();
                log.setAction(action.toUpperCase());
                logRepository.save(log);
            }

        }
    }
    @AfterReturning(pointcut="execution(* ma.donasid.recrute.controllers.CandidatController.postuler(..) )",returning = "result")
    void savePostingOfferLog(JoinPoint join, ResponseEntity<?> result){
        Principal principal=null;
        Long id=null;
        Object[] args =join.getArgs();
        for (Object obj:args) {
            if(obj instanceof Long)
                 id= (Long) obj;

            if(obj instanceof Principal)
                principal= (Principal) obj;
        }
        if((result.getStatusCodeValue()==200)||(result.getStatusCodeValue()==201)){
            Candidature candidature = (Candidature) result.getBody();
            String action= "Creation Candidature "+candidature.getCode()+"   Reussie ";
            Log log = new Log(action.toUpperCase(), candidature.getOwner());
            logRepository.save(log);
        }else{
            String action= " Creation Candidature  Echoue ";
            User user =userRepository.findByEmail(principal.getName());
            Log log = new Log();
            log.setId(Long.valueOf(0));

            log.setAction(action.toUpperCase());
            logRepository.save(log);
        }
    }

    @AfterReturning(pointcut="execution(* ma.donasid.recrute.controllers.AdminController.validerOffer(..) )",returning = "result")
    void saveValidationOfferLog(JoinPoint join, ResponseEntity<?> result){
        Principal principal=null;
        Long id=null;
        Object[] args =join.getArgs();
        for (Object obj:args) {
            if(obj instanceof Long)
                id= (Long) obj;

            if(obj instanceof Principal)
                principal= (Principal) obj;
        }
        if((result.getStatusCodeValue()==200)||(result.getStatusCodeValue()==201)){
            String action= " Offer"+id+" Validation  Reussie ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }else{
            String action= " Offre"+id+" Validation user   Echoue ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }

    }
    @AfterReturning(pointcut="execution(* ma.donasid.recrute.controllers.RecruterController.changerStatusCandidature(..))",returning = "result")
    void saveValidationCandidatureLog(JoinPoint join, ResponseEntity<?> result){
        Principal principal=null;
        Long id=null;
        String status=null;
        Object[] args =join.getArgs();
        for (Object obj:args) {
            if(obj instanceof Long)
                id= (Long) obj;
            if(obj instanceof String)
                status= (String) obj;
            if(obj instanceof Principal)
                principal= (Principal) obj;
        }
        if((result.getStatusCodeValue()==200)||(result.getStatusCodeValue()==201)){

            String action= "changement de status candidature"+id+"   Reussie ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }else{
            String action= "Changement de status Candidature "+id+"    Echoue ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }

    }

    @AfterReturning(pointcut="execution(* ma.donasid.recrute.controllers.AdminController.suspendUser(..))",returning = "result")
    void saveUserSuspendedLog(JoinPoint join, ResponseEntity<?> result){
        Principal principal=null;
        Long id=null;
        Object[] args =join.getArgs();
        for (Object obj:args) {
            if(obj instanceof Long)
                id= (Long) obj;

            if(obj instanceof Principal)
                principal= (Principal) obj;
        }
        if((result.getStatusCodeValue()==200)||(result.getStatusCodeValue()==201)){

            String action= "suspension user"+id+"   Reussie ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }else{
            String action= "suspension user "+id+"     Echoue ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }

    }

    @AfterReturning(pointcut="execution(* ma.donasid.recrute.controllers.RecruterController.cloturerOffer(..))",returning = "result")
    void saveOfferClotureLog(JoinPoint join, ResponseEntity<?> result){
        Principal principal=null;
        Long id=null;
        Object[] args =join.getArgs();
        for (Object obj:args) {
            if(obj instanceof Long)
                id= (Long) obj;

            if(obj instanceof Principal)
                principal= (Principal) obj;
        }
        if((result.getStatusCodeValue()==200)||(result.getStatusCodeValue()==201)){

            String action= "Validation Offer"+id+"   Reussie ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }else{
            String action= "Validation Candidature "+id+"     Echoue ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }

    }

    @AfterReturning(pointcut="execution(* ma.donasid.recrute.controllers.AdminController.changerRole() )",returning = "result")
    void saveUserChangingStatusLog(JoinPoint join, ResponseEntity<?> result){
        Principal principal=null;
        Long id=null;
        Object[] args =join.getArgs();
        for (Object obj:args) {
            if(obj instanceof Long)
                id= (Long) obj;

            if(obj instanceof Principal)
                principal= (Principal) obj;
        }
        if((result.getStatusCodeValue()==200)||(result.getStatusCodeValue()==201)){
            Offer offer = (Offer) result.getBody();
            String action= "Changement de status de l user "+id+"   Reussie ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }else{
            String action= "Changement de status de l user  "+id+"     Echoue ";
            User user =userRepository.findByEmail(principal.getName());

            Log log = new Log(action.toUpperCase(), user);
            logRepository.save(log);
        }

    }



}
