package gr.aueb.cf.mutu.models_prod;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    private Integer height;
    private Integer weight;
    private String bio;

    public User(long id, String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.bio = bio;
    }

    public User() {

    }

//    public User(String email, String password, String name, LocalDate birthday,  Integer height, Integer weight, String bio) {
//        this.id = users.size() + 1;
//        this.email = email;
//        this.name = name;
//        this.birthday = birthday;
//        this.password = password;
//        this.height = height;
//        this.weight = weight;
//        this.bio = bio;
//    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
