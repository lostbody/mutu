package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.models.User;
import gr.aueb.cf.mutu.models.UserAction;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/swipe")
public class Swipe extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User loggedUser = Authentication.getSessionUser(request);

        if (loggedUser == null) {
            // If no user is logged in, redirect to the login page
            response.sendRedirect("login.jsp");
            return;
        }

        int otherUserId;
        try {
            otherUserId = Integer.parseInt(request.getParameter("other-user-id"));
        }
        catch (NumberFormatException e) {
            response.sendRedirect("swipe-page.jsp");
            return;
        }

        UserAction userAction = UserAction.getByUserIds(loggedUser.getId(), otherUserId);
        String typedAction = request.getParameter("swipe-direction");

        UserAction.Action action;
        if (typedAction.equals("SWIPE-LEFT")) {
            action = UserAction.Action.SWIPE_LEFT;
        } else{
            action = UserAction.Action.SWIPE_RIGHT;
        }

        if (userAction != null) {
            if (userAction.getUser1() == loggedUser.getId()) {
                userAction.setUser1_action(action);
            } else {
                userAction.setUser2_action(action);
            }
        } else {
            userAction = new UserAction(loggedUser.getId(), otherUserId, action, null);
            UserAction.userActions.add(userAction);
        }

        response.sendRedirect("swipe-page.jsp");
    }
}
