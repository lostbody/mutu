package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IUserDao;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;

public class UserDao implements IUserDao {

    @Override
    public UserDto getById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return user == null ? null : user.toDto();
        }
    }

    @Override
    public UserDto getByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<User> query = session.createNativeQuery("""
                SELECT * FROM users WHERE email = :email
            """, User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();
            return user == null ? null : user.toDto();
        }
    }

    @Override
    public UserDto getUserByCredentials(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<User> query = session.createNativeQuery("""
                SELECT * FROM users WHERE email = :email AND password = :password
            """, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            User user = query.uniqueResult();
            return user == null ? null : user.toDto();
        }
    }

    @Override
    public UserDto createUser(String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio) {
        return null;
    }

    @Override
    public void updateUser(UserDto loggedUser) {

    }

    @Override
    public UserDto getPotentialMatch(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<User> query = session.createNativeQuery("""
                SELECT * FROM users WHERE id != :id LIMIT 1
            """, User.class);
            query.setParameter("id", id);
            User user = query.uniqueResult();
            return user.toDto();
        }
    }
}
