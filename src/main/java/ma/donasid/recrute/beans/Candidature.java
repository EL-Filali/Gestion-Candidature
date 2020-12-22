package ma.donasid.recrute.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Candidature {
    @Id @GeneratedValue
    private Long code;


    private String lettre;

    private Date postedAt;

    private String status;
    @ElementCollection(targetClass=String.class)
    private List<String> answers;

    @ManyToOne
    @JoinColumn(name="idOffer")
    private Offer theOffer;


    @ManyToOne
    @JoinColumn(name="idOwner")
    private User owner;







    public Long getId() {
        return code;
    }

    public void setId(Long id) {
        this.code = id;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Offer getOffer() {
        return theOffer;
    }

    public void setOffer(Offer offer) {
        this.theOffer = offer;
    }



}
