package ma.donasid.recrute.repositories;

import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature,Long> {
    public List<Candidature> findByOwner(User owner) ;
    public Candidature findByTheOfferAndCode(Offer offer, Long Code);
    public Candidature findByOwnerAndCode(User owner,Long code);
}
