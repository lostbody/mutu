package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IUserDao;
import gr.aueb.cf.mutu.dto.UserDto;

import java.time.LocalDate;

public class UserDao implements IUserDao {
    @Override
    public UserDto getUserByCredentials(String email, String password) {
        return null;
    }

    @Override
    public UserDto createUser(String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio) {
        return null;
    }

    @Override
    public void updateUser(UserDto loggedUser) {

    }

    @Override
    public UserDto getByEmail(String email) {
        return null;
    }

    @Override
    public UserDto getPotentialMatch(long id) {
        return null;
    }

    @Override
    public UserDto getById(int matchId) {
        return null;
    }
}
