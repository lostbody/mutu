package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dto.UserActionDto;
import jakarta.persistence.*;

@Entity
@Table(name = "user_actions")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @Enumerated(EnumType.STRING)
    @Column(name = "user1_action", nullable = false)
    private UserActionDto.Action user1Action;

    @Enumerated(EnumType.STRING)
    @Column(name = "user2_action", nullable = false)
    private UserActionDto.Action user2Action;

    public UserAction() {}

    public UserAction(User user1, User user2, UserActionDto.Action user1Action, UserActionDto.Action user2Action) {
        this.user1 = user1;
        this.user2 = user2;
        this.user1Action = user1Action;
        this.user2Action = user2Action;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public UserActionDto.Action getUser1Action() {
        return user1Action;
    }

    public void setUser1Action(UserActionDto.Action user1Action) {
        this.user1Action = user1Action;
    }

    public UserActionDto.Action getUser2Action() {
        return user2Action;
    }

    public void setUser2Action(UserActionDto.Action user2Action) {
        this.user2Action = user2Action;
    }

    public UserActionDto toDto() { return new UserActionDto(user1.getId(), user2.getId(), user1Action, user2Action); }
}
