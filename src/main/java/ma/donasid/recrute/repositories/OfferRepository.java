package ma.donasid.recrute.repositories;

import ma.donasid.recrute.beans.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
