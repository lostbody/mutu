package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IUserActionDao;
import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;
import java.util.stream.Collectors;

public class UserActionDao implements IUserActionDao {
    @Override
    public UserActionDto getByUserIds(long user1Id, long user2Id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<UserAction> query = session.createNativeQuery("""
                SELECT * FROM user_actions
                WHERE
                    (user1_Id = :user1Id AND user2_id = :user2Id) OR
                    (user1_Id = :user2Id AND user2_Id = :user1Id)
            """, UserAction.class);
            query.setParameter("user1Id", user1Id);
            query.setParameter("user2Id", user2Id);
            UserAction userAction = query.uniqueResult();
            return userAction == null ? null : userAction.toDto();
        }
    }

    @Override
    public UserActionDto createUserAction(long user1Id, long user2Id, UserActionDto.Action user1Action, UserActionDto.Action user2Action) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user1 = session.get(User.class, user1Id);
            User user2 = session.get(User.class, user2Id);
            if (user1 == null || user2 == null) {
                throw new IllegalArgumentException("Invalid user IDs provided.");
            }

            UserAction userAction = new UserAction(user1, user2, user1Action, user2Action);
            session.persist(userAction);

            transaction.commit();
            return userAction.toDto();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }

    @Override
    public void updateUserAction(UserActionDto userActionDto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            NativeQuery<UserAction> query = session.createNativeQuery("""
                SELECT * FROM user_actions
                WHERE
                    (user1_id = :user1Id AND user2_id = :user2Id) OR
                    (user1_id = :user2Id AND user2_id = :user1Id)
                LIMIT 1
            """, UserAction.class);
            query.setParameter("user1Id", userActionDto.getUser1());
            query.setParameter("user2Id", userActionDto.getUser2());

            UserAction userAction = query.uniqueResult();
            if (userAction == null) {
                throw new IllegalArgumentException("UserAction not found.");
            }

            userAction.setUser1Action(userActionDto.getUser1_action());
            userAction.setUser2Action(userActionDto.getUser2_action());

            session.merge(userAction);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }

    @Override
    public List<UserDto> getMatchesByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<User> query = session.createNativeQuery("""
                SELECT u.*
                FROM users u
                JOIN user_actions ua
                  ON (ua.user1_id = u.id OR ua.user2_id = u.id)
                WHERE
                  (ua.user1_id = :userId OR ua.user2_id = :userId) AND
                  u.id != :userId AND
                  ua.user1_action = 'SWIPE_RIGHT' AND
                  ua.user2_action = 'SWIPE_RIGHT'
            """, User.class);
            query.setParameter("userId", userId);
            List<User> matchedUsers = query.getResultList();

            return matchedUsers.stream().map(User::toDto).collect(Collectors.toList());
        }
    }
}


