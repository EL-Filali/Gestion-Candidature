package ma.GestionCandidature.gestion.candidature.repositories;

import ma.GestionCandidature.gestion.candidature.beans.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
