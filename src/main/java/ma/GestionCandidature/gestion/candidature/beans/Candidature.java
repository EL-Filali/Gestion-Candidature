package ma.GestionCandidature.gestion.candidature.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Candidature {
    @Id @GeneratedValue
    private Long id;
    private String lettre;
    private Date postedAt;
    private String status;
    private List<String> answers;
    @ManyToOne @JoinColumn(name="id")
    private Candidat Owner;
    @ManyToOne @JoinColumn(name="id")
    private Offer offer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLettre() {
        return lettre;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Candidat getOwner() {
        return Owner;
    }

    public void setOwner(Candidat owner) {
        Owner = owner;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }



}
