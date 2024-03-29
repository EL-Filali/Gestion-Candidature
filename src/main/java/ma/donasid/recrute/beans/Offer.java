package ma.donasid.recrute.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Offer {
    @Id@GeneratedValue
    private Long id;

    private Date createdAt;

    private String title;
    private String domaine;
    private String description;
    private String status;
    @ElementCollection(targetClass=String.class)
    private List<String> kWs;
    @ElementCollection(targetClass=String.class)
    private List<String> questions;


    @JsonIgnore
    @OneToMany(mappedBy = "theOffer", fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Candidature> candidatures;

    @PrePersist
    void initOffer(){
        this.createdAt= new Date();
        this.status="NON_VALID";
    }
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="idOwner")
    private User owner;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getkWs() {
        return kWs;
    }

    public void setkWs(List<String> kWs) {
        this.kWs = kWs;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
