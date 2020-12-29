package ma.donasid.recrute.repositories;

import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature,Long> {
    public List<Candidature> findByOwner(User owner) ;

}