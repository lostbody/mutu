package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dto.UserDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import gr.aueb.cf.mutu.models_prod.Interest;
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String password;

    @Column
    private Integer height;

    @Column
    private Integer weight;

    @Column
    private String bio;

    @ManyToMany
    @JoinTable(
            name = "user_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Picture> pictures = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Set<Interest> getInterests() { return interests; }
    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }
    public Set<Picture> getPictures() {
        return pictures;
    }
    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public UserDto toDto() {
        return new UserDto(id, email, password, name, birthday, height, weight, bio);
    }
}
