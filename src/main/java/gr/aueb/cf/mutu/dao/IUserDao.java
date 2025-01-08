package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.models_prod.Interest;

import java.time.LocalDate;
import java.util.Set;

public interface IUserDao {
    UserDto createUser(String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio);

    void updateUser(UserDto loggedUser);

    UserDto getByEmail(String email);

    UserDto getPotentialMatch(long id);

    UserDto getById(long matchId);
}
