package gr.aueb.cf.mutu.utils;

import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.models_prod.UserActionDao;

import java.util.List;


public class UserActionTest {

    public static void main(String[] args) {
        try {
            testCreateSwipe();
            testUpdateSwipe();
            testFetchMatches();
        } catch (Exception e) {
            System.err.println("Error during testCreateSwipe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testCreateSwipe() {
        UserActionDao userActionDao = new UserActionDao();

        try {
            UserActionDto swipeAction = userActionDao.createUserAction(1L, 3L, UserActionDto.Action.SWIPE_RIGHT, UserActionDto.Action.SWIPE_LEFT);
            System.out.println("Created Swipe Action: " + swipeAction);
        } catch (Exception e) {
            System.err.println("Error creating swipe action: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testUpdateSwipe() {
        UserActionDao userActionDao = new UserActionDao();

        UserActionDto swipe = userActionDao.getByUserIds(1L, 2L);
        if (swipe != null) {
            swipe.setUser2_action(UserActionDto.Action.SWIPE_LEFT);
            userActionDao.updateUserAction(swipe);
            System.out.println("Updated Swipe Action: " + swipe);
        } else {
            System.out.println("No swipe action found.");
        }
    }

    public static void testFetchMatches() {
        UserActionDao userActionDao = new UserActionDao();

        // Fetch matches for Gina
        List<UserDto> matches = userActionDao.getMatchesByUserId(1L);
        System.out.println("Matches for Gina:");
        matches.forEach(match -> System.out.println(match.getName()));
    }


}