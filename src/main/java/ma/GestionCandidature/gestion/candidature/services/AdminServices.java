package ma.GestionCandidature.gestion.candidature.services;

import ma.GestionCandidature.gestion.candidature.beans.User;
import ma.GestionCandidature.gestion.candidature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {
    @Autowired
    UserRepository userRepository;

   public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void save(User user){
       userRepository.save(user);
    }

}
