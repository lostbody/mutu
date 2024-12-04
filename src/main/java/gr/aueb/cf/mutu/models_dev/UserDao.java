package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dao.IUserDao;
import gr.aueb.cf.mutu.dto.UserDto;

import java.time.LocalDate;

import static gr.aueb.cf.mutu.models_dev.User.users;

public class UserDao implements IUserDao {
    @Override
    public UserDto getUserByCredentials(String email, String password) {
        User user = User.getUserByCredentials(email, password);
        return user == null ? null : user.toDto();
    }

    @Override
    public UserDto createUser(String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio) {
        User user = new User(email, password, name, birthday, null, null, null);
        users.add(user);
        return user.toDto();
    }

    @Override
    public void updateUser(UserDto loggedUser) {
        User user = User.getById(loggedUser.getId());
        user.setPassword(loggedUser.getPassword());
        user.setHeight(loggedUser.getHeight());
        user.setWeight(loggedUser.getWeight());
        user.setBio(loggedUser.getBio());
    }
}
