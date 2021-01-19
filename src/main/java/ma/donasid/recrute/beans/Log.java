package ma.donasid.recrute.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Log {
    @Id @GeneratedValue
    private Long id;

    private String action;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;
    @JsonIgnore
    private Date date;
    @PrePersist
    void init(){
        date=new Date();
    }
    public Log() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Log(String action, User user) {
        this.action = action;
        this.user = user;
    }

}
