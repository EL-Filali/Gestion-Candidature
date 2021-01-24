package ma.donasid.recrute.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.config.JwtTokenProvider;
import ma.donasid.recrute.responses.ConnexionRequest;
import ma.donasid.recrute.responses.ConnexionResponse;
import ma.donasid.recrute.services.RecruterServices;
import ma.donasid.recrute.services.ValidationServices;
import ma.donasid.recrute.services.VisitorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static ma.donasid.recrute.config.SecurityConstants.TOKEN_PREFIX;



@RestController
@RequestMapping("/api")
public class VisitorController {
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    ValidationServices validationServices;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    VisitorServices visitorServices;
    @Autowired
    RecruterServices recruterServices;

    @GetMapping("/offers")
     ResponseEntity<?> getAllOffers(){

        return visitorServices.getAllOffers();

    }
    @GetMapping("/offers/{id}")
    ResponseEntity<?> getOffers(@PathVariable Long id){
        try{

            return visitorServices.getOffer(id);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@Valid @RequestBody ConnexionRequest loginResponse, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try{    Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginResponse.getEmail(),loginResponse.getPassword())
                );
            ObjectMapper mapper = new ObjectMapper();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
            User user = (User) authentication.getPrincipal();
            ConnexionResponse connexionResponse =new ConnexionResponse(jwt, user);


            return new ResponseEntity<String>(mapper.writeValueAsString(connexionResponse.toHashMap()), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex,HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping("/register")
    public  ResponseEntity<?>   register(@Valid @RequestBody User user, BindingResult result) throws IOException {

        if(result.hasErrors())
            return validationServices.MapValidationService(result);
        else{
            String password= user.getPassword();
            String email=user.getEmail();
            visitorServices.userRegister(user);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            ObjectMapper mapper = new ObjectMapper();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

            ConnexionResponse connexionResponse =new ConnexionResponse(jwt, user);
            return new ResponseEntity<>(mapper.writeValueAsString(connexionResponse.toHashMap()), HttpStatus.OK);
        }


    }

    @GetMapping("/lastoffers")
    ResponseEntity<?> getLastOffers(){
        return visitorServices.getLastestOffer();
    }
}
