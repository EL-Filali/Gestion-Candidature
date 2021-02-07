package ma.donasid.recrute.repositories;

import ma.donasid.recrute.beans.Offer;
import ma.donasid.recrute.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    public List<Offer> findByStatus(String status);
    public List<Offer> findTop3ByStatusOrderByCreatedAt(String status);
    public List<Offer> findByOwner(User owner);
}
