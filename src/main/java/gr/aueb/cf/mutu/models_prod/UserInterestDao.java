package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IUserInterestDao;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;
import java.util.stream.Collectors;

public class UserInterestDao implements IUserInterestDao {

    @Override
    public void setUserInterests(long userId, Set<Long> interestIds) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Set<Interest> interests = interestIds.stream()
                    .map(interestId -> session.get(Interest.class, interestId))
                    .collect(Collectors.toSet());

            User user = session.get(User.class, userId);
            user.setInterests(interests);
            session.merge(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
    }
}
