package ma.donasid.recrute.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sun.plugin2.message.Message;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class User implements UserDetails  {
    @Id @GeneratedValue
    private Long id;
    @NotBlank
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{3,6}")
    @Column(unique = true)
    private String CIN;
    @Email @Column(unique = true)
    @NotBlank
    private String email;

    @Column(unique = true)

    @NotBlank
    @Pattern(regexp = "^(?:(?=.*[a-z])(?:(?=.*[A-Z])(?=.*[\\d\\W])|(?=.*\\W)(?=.*\\d))|(?=.*\\W)(?=.*[A-Z])(?=.*\\d)).{8,}$",message = "Le motdepasse doit  contenir des caractères d'au moins 3 des 4 regles suivantes:\n" +

            "Majiscule" +
            "Minuscules" +
            "Nombres" +
            "Non alphanumérique")
    private String password;
    @Pattern(regexp = "[A-Za-z ]{2,}$",message = "Le Champs doix contenir Min 2 caractere Alphabetique")
    private String firstName;
    @Pattern(regexp = "[A-Za-z ]{2,}$",message = "Le Champs doix contenir Min 2 caractere Alphabetique")
    private String lastName;
    @NotBlank
    private String adress;
    @Pattern(regexp = "^06[0-9]{8}$",message = "Entrer un numero de telephone valide 06XXXXXXXX")
    private String phoneNumber;

    private String role;
    @JsonIgnore
    private Boolean enabled;
    @JsonIgnore
    private Boolean accountExpired;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updatedAt;
    @OneToMany(mappedBy = "owner")
    private List<Candidature> candidatures;
    @OneToMany(mappedBy = "owner")
    private List<Offer> offers;




    @PrePersist
    protected void onCreation()
    {
        createdAt =new Date();
        enabled=true;
        accountExpired=false;
        role="CANDIDAT";
    }

    @PreUpdate
    protected  void onUpdate(){
        updatedAt=new Date();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getupdatedAt() {
        return updatedAt;
    }

    public void setupdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
