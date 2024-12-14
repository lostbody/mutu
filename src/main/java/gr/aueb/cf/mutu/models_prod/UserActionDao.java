package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IUserActionDao;
import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class UserActionDao implements IUserActionDao {
    @Override
    public UserActionDto getByUserIds(long user1Id, long user2Id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<UserAction> query = session.createNativeQuery("""
                SELECT *
                FROM user_actions
                WHERE
                    (user1_Id = :a AND user2_id = :b) OR (user1_Id = :b AND user2_Id = :a)
            """, UserAction.class);
            query.setParameter("a", user1Id);
            query.setParameter("b", user2Id);
            UserAction userAction = query.uniqueResult();
            return userAction == null ? null : userAction.toDto();
        }
    }

    @Override
    public UserActionDto createUserAction(long user1Id, long user2Id, UserActionDto.Action user1Action, UserActionDto.Action user2Action) {
        return null;
    }

//    @Override
//    public UserActionDto createUserAction(long user1Id, long user2Id, UserActionDto.Action user1Action, UserActionDto.Action user2Action) {
////        """
////            INSERT INTO user_actions ...
////        """
//    }

    @Override
    public void updateUserAction(UserActionDto userActionDto) {
//        UserAction userAction = UserAction.fromDto(userActionDto);
//        session.persist(userAction);
    }

    @Override
    public List<UserDto> getMatchesByUserId(long userId) {
return null;
    }
}
