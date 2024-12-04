package gr.aueb.cf.mutu.models_dev;
import gr.aueb.cf.mutu.dto.UserDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    public static List<User> users = new ArrayList<>();
    static {
        users.add(new User(1, "gina@gina.com", "gina","Gina", LocalDate.of(1987 - 1900,9 - 1,1),160, null, "loremipsum"));
        users.add(new User(2, "rodia@rodia.com", "rodia","Rodia", LocalDate.of(1991 - 1900, 10 - 1, 15),160, null, "loremipsum"));
        users.add(new User(3, "dora@dora.com", "dora","Dora", LocalDate.of(1995 - 1900, 11 - 1, 22),160, null, "loremipsum"));
        users.add(new User(4, "kostis@kostis.com", "kostis","Kostis", LocalDate.of(1988 - 1900, 10 - 1, 24),183, null, "loremipsum"));
        users.add(new User(5, "andreas@andreas.com", "andreas", "Andreas", LocalDate.of(1991 - 1900, 4 - 1, 24), 175, null, "loremipsum"));
    }

    public static User getById(long userId) {
        return users
                .stream()
                .filter(u -> u.id == userId)
                .findFirst()
                .orElse(null);
    }

    public static User getUserByCredentials(String email, String password) {
        return users
            .stream()
            .filter(u -> u.email.equals(email) && u.password.equals(password))
            .findFirst()
            .orElse(null);
    }
    private final long id;
    private final String email;
    private final String name;
    private final LocalDate birthday;
    private String password;
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

    public User(String email, String password, String name, LocalDate birthday,  Integer height, Integer weight, String bio) {
        this.id = users.size() + 1;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.password = password;
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

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", weight=" + weight +
                ", bio='" + bio + '\'' +
                '}';
    }

    public UserDto toDto() {
        return new UserDto(id, email, password, name, birthday, height, weight, bio);
    }
}
