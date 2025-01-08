package gr.aueb.cf.mutu.dto;
import gr.aueb.cf.mutu.models_dev.Interest;

import java.time.LocalDate;
import java.util.Set;

public class UserDto {

    private final long id;
    private final String email;
    private final String name;
    private final LocalDate birthday;
    private String hashedPassword;
    private Integer height;
    private Integer weight;
    private String bio;

    public UserDto(long id, String email, String hashedPassword, String name, LocalDate birthday, Integer height, Integer weight, String bio) {
        this.id = id;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.bio = bio;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + hashedPassword + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", weight=" + weight +
                ", bio='" + bio + '\'' +
                '}';
    }
}
