package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IInterestDao;
import gr.aueb.cf.mutu.dto.InterestDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterestDao implements IInterestDao {
    @Override
    public List<InterestDto> getInterestsByUserId(long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return new ArrayList<>(user.getInterests()).stream()
                    .map(Interest::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<InterestDto> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<Interest> query = session.createNativeQuery("""
                SELECT *
                FROM interests
            """, Interest.class);
            return query.getResultList().stream()
                    .map(Interest::toDto)
                    .collect(Collectors.toList());
        }
    }
}
