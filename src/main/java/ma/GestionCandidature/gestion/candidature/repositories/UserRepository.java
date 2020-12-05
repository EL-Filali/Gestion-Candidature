package ma.GestionCandidature.gestion.candidature.repositories;

import ma.GestionCandidature.gestion.candidature.beans.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Candidat, Long> {
}
