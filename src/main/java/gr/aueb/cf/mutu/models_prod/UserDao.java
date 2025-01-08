package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IUserDao;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.Set;

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
    public UserDto createUser(String email, String password, String name, LocalDate birthday, Integer height, Integer weight, String bio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = new User();
            user.setEmail(email);
            user.setHashedPassword(password);
            user.setName(name);
            user.setBirthday(birthday);
            user.setHeight(height);
            user.setWeight(weight);
            user.setBio(bio);

            session.persist(user);

            transaction.commit();

            System.out.println(
                    "Created user with height" + user.getHeight()
            );

            return user.toDto();

        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }

    @Override
    public void updateUser(UserDto userDto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, userDto.getId());
            if (user == null) {
                throw new IllegalArgumentException("User not found.");
            }

            user.setHashedPassword(userDto.getHashedPassword());
            user.setWeight(userDto.getWeight());
            user.setBio(userDto.getBio());
            session.merge(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }

    @Override
    public UserDto getPotentialMatch(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<User> query = session.createNativeQuery("""
                SELECT
                    u.*
                FROM
                    users u
                WHERE
                    u.id != :userId
                    AND NOT EXISTS (
                        SELECT
                            1
                        FROM
                            user_actions ua
                        WHERE
                            (ua.user1_id = :userId AND ua.user2_id = u.id AND ua.user1_action IS NOT NULL) OR
                            (ua.user2_id = :userId AND ua.user1_id = u.id AND ua.user2_action IS NOT NULL)
                    )
                ORDER BY
                    RAND()
                LIMIT 1
            """, User.class);
            query.setParameter("userId", userId);
            User user = query.uniqueResult();
            return user.toDto();
        }
    }
}
