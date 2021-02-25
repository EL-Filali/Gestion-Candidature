package ma.donasid.recrute.repositories;

import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.beans.User;
import ma.donasid.recrute.beans.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature,Long> {
    public List<Candidature> findByOwner(User owner) ;
    public Candidature findByTheOfferAndCode(Offer offer, Long Code);
    public Candidature findByOwnerAndCode(User owner,Long code);
    public List<Candidature> findByTheOffer(Offer offer);

    Candidature findByCodeAndTheOffer( Long code, Offer theOffer);
    @Modifying
    @Query("update Candidature u set u.status = 'REFUSEE' where u.status <> 'VALIDEE' and u.theOffer= :theOffer " )
    public void updateCandidatureAfterOfferExpired(Offer theOffer);
    public void deleteAllByOwner(User owner);
}
