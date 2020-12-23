package ma.donasid.recrute.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.config.JwtTokenProvider;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<?> connexion(@Valid @RequestBody ConnexionResponse loginResponse, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try{    Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginResponse.getEmail(),loginResponse.getPassword())
                );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
            return new ResponseEntity<String>(jwt, HttpStatus.OK);
        }catch (SignatureException ex){
            return new ResponseEntity<String>("Invalid JWT Signature",HttpStatus.NOT_ACCEPTABLE);
        }catch (MalformedJwtException ex){
            return new ResponseEntity<String>("Invalid JWT Token",HttpStatus.NOT_ACCEPTABLE);
        }catch (ExpiredJwtException ex){
            return new ResponseEntity<String>("Expired JWT token",HttpStatus.NOT_ACCEPTABLE);
        }catch (UnsupportedJwtException ex){
            return new ResponseEntity<String>("Unsupported JWT token",HttpStatus.NOT_ACCEPTABLE);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<String>("JWT claims string is empty",HttpStatus.NOT_ACCEPTABLE);
        }


    }

    @PostMapping("/register")
    public  ResponseEntity<?>   register(@Valid @RequestBody User user, BindingResult result){

        if(result.hasErrors())
            return validationServices.MapValidationService(result);
        else
            return visitorServices.userRegister(user);

    }
}
