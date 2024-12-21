package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IMessageDao;
import gr.aueb.cf.mutu.dto.MessageDto;
import gr.aueb.cf.mutu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class MessageDao implements IMessageDao {

    @Override
    public List<MessageDto> getNewMessagesByUserIds(long user1Id, long user2Id, long since) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            NativeQuery<Message> query = session.createNativeQuery("""
                SELECT * FROM messages
                WHERE
                    ((sender_id = :user1Id AND receiver_id = :user2Id) OR 
                     (sender_id = :user2Id AND receiver_id = :user1Id))
                    AND timestamp > :since
                ORDER BY timestamp
            """, Message.class);
            query.setParameter("user1Id", user1Id);
            query.setParameter("user2Id", user2Id);
            query.setParameter("since", new Timestamp(since));
            return query.getResultList().stream()
                    .map(Message::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public MessageDto createMessage(long userId, long matchId, String content, long timestamp) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User sender = session.get(User.class, userId);
            User receiver = session.get(User.class, matchId);
            if (sender == null || receiver == null) {
                throw new IllegalArgumentException("Invalid sender or receiver ID");
            }

            Message message = new Message(sender, receiver, content, new Timestamp(timestamp));
            session.persist(message);

            transaction.commit();
            return message.toDto();
        } catch (Exception e) {
            if (transaction != null) { transaction.rollback(); }
            throw e;
        }
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
