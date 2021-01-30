package ma.donasid.recrute.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Candidature {
    @Id @GeneratedValue
    private Long code;

    @Lob
    @Column
    private String motivation;



    @ElementCollection(targetClass=String.class)
    private List<String> answers;



    private Date postedAt;

    private String status;
    
    @ManyToOne
    private Offer theOffer;


    @ManyToOne
    private User owner;


    @PrePersist
    void initCandidature(){
        this.postedAt= new Date();
        this.status="EN_ATTENTE";
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
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

    public Offer getTheOffer() {
        return theOffer;
    }

    public void setTheOffer(Offer theOffer) {
        this.theOffer = theOffer;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
