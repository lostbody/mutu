package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.UserDto;

import java.time.LocalDate;

public interface IUserDao {
    UserDto getUserByCredentials(String email, String password);

    UserDto createUser(String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio);

    void updateUser(UserDto loggedUser);
}
