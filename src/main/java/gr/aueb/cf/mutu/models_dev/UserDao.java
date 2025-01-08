package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dao.IUserDao;
import gr.aueb.cf.mutu.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static gr.aueb.cf.mutu.models_dev.User.users;

public class UserDao implements IUserDao {

    @Override
    public UserDto createUser(String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio) {
        User user = new User(email, password, name, birthday, height, weight, bio);
        users.add(user);
        return user.toDto();
    }

    @Override
    public void updateUser(UserDto loggedUser) {
        User user = User.getById(loggedUser.getId());
        user.setPassword(loggedUser.getHashedPassword());
        user.setHeight(loggedUser.getHeight());
        user.setWeight(loggedUser.getWeight());
        user.setBio(loggedUser.getBio());
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = User.getByEmail(email);
        return user == null ? null : user.toDto();
    }

    @Override
    public UserDto getPotentialMatch(long id) {
        List<User> otherUsers = User.users.stream()
                .filter(u -> u.getId() != id)
                .collect(Collectors.toList());
        return otherUsers.get(new Random().nextInt(otherUsers.size())).toDto();
    }

    @Override
    public UserDto getById(long id) {
        User user = User.getById(id);
        return user == null ? null : user.toDto();
    }
}
