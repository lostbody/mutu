package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IMessageDao;
import gr.aueb.cf.mutu.dto.MessageDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageDao implements IMessageDao {

    @Override
    public List<MessageDto> getNewMessagesByUserIds(long user1Id, long user2Id, long since) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<Message> query = session.createNativeQuery("""
                SELECT *
                FROM messages
                WHERE
                    (sender_id = :a AND receiver_id = :b) OR (sender_id = :b AND receiver_id = :a)
                    AND timestamp > :since
            """, Message.class);
            query.setParameter("a", user1Id);
            query.setParameter("b", user2Id);
            query.setParameter("since", since);
            return query.getResultList().stream()
                    .map(Message::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public MessageDto createMessage(long userId, long matchId, String content, long timestamp) {
        return null;
    }

    @Override
    public List<MessageDto> getConversationByUserIds(long user1Id, long user2Id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<Message> query = session.createNativeQuery("""
                SELECT *
                FROM messages
                WHERE
                    (sender_id = :a AND receiver_id = :b) OR (sender_id = :b AND receiver_id = :a)
            """, Message.class);
            query.setParameter("a", user1Id);
            query.setParameter("b", user2Id);
            return query.getResultList().stream()
                    .map(Message::toDto)
                    .collect(Collectors.toList());
        }
    }

}
