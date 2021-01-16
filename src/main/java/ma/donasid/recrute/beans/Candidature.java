package ma.donasid.recrute.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Candidature {
    @Id @GeneratedValue
    private Long code;


    private String motivation;


    @ElementCollection(targetClass=String.class)
    private List<String> answers;



    private Date postedAt;
    
    private String status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="idOffer")
    private Offer theOffer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="idOwner")
    private User owner;


    @PrePersist
    void initCandidature(){
        this.postedAt= new Date();
        this.status="EN_ATTENTE";
    }





    public Long getId() {
        return code;
    }

    public void setId(Long id) {
        this.code = id;
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

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public Offer getTheOffer() {
        return theOffer;
    }

    public void setTheOffer(Offer theOffer) {
        this.theOffer = theOffer;
    }
}
