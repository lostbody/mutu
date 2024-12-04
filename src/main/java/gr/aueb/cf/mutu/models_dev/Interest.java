package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dto.InterestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Interest {
    public static List<Interest> interests = new ArrayList<>();
    static {
        interests.add(new Interest(1, "dnd"));
        interests.add(new Interest(2, "baseball"));
        interests.add(new Interest(3, "gaming"));
        interests.add(new Interest(4, "bridge"));
        interests.add(new Interest(5, "tennis"));
    }

    //μια μέθοδος που μας επιστρέφει InterestDto interest από το List<InterestDto> όταν της δίνουμε
    // σαν όρισμα ένα interestId αντιστοιχίζοντας το στο id της κλάσης InterestDto. Tο interestId είναι
    // foreign key σε άλλο class-table που σχετίζεται με το InterestDto. Ο UserDto έχει πολλά
    //interests οπότε έχει το πεδίο interestId ως foreignkey ωστε να μπορούμε να τα αντιστοιχήσουμε σε ένα
    // join table.

    public static Interest getInterestById(long interestId) {
        return interests
                .stream()
                .filter(i -> i.id == interestId)
                .findFirst()
                .orElse(null);
    }

    //εδώ έχουμε μια μέθοδο για να παίρνουμε ένα List<InterestDto> δίνονταν το id ενός user.
    //Χρησιμοποιουμε την κλάση UserInterest και το πεδίο userId που είναι foreignkey για να αντιστοιχίζεται με
    //τους users.

    public static List<Interest> getInterestsByUserId(long userId) {
        List<UserInterest> userInterests = UserInterest.getUserInterestsByUserId(userId);
        return userInterests
                .stream()
                .map(ui -> getInterestById(ui.getInterestId()))
                .collect(Collectors.toList());
    }

    private final long id;
    private final String name;

    public Interest(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "InterestDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public InterestDto toDto() {
        return new InterestDto(id, name);
    }
}
