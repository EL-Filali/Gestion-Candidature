package ma.GestionCandidature.gestion.candidature.beans;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Offer {
    @Id@GeneratedValue
    private Long id;

    private Date createdAt;

    private String title;

    private String status;
    @ElementCollection(targetClass=String.class)
    private List<String> kWs;
    @ElementCollection(targetClass=String.class)
    private List<String> questions;



    @OneToMany(mappedBy = "theOffer", fetch=FetchType.EAGER)
    private List<Candidature> candidatures;
    @PrePersist
    void initOffer(){
        this.createdAt= new Date();
        this.status="NON_VALID";
    }

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
}
