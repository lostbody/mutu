package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dto.UserActionDto;
import gr.aueb.cf.mutu.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import gr.aueb.cf.mutu.service.UserActionService;

import java.io.IOException;

@WebServlet("/swipe")
public class Swipe extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto loggedUser = Authentication.getSessionUser(request);
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

        UserActionDto userAction = UserActionService.getImpl().getByUserIds(loggedUser.getId(), otherUserId);
        String typedAction = request.getParameter("swipe-direction");

        UserActionDto.Action action;
        if (typedAction.equals("SWIPE-LEFT")) {
            action = UserActionDto.Action.SWIPE_LEFT;
        } else{
            action = UserActionDto.Action.SWIPE_RIGHT;
        }

        if (userAction != null) {
            if (userAction.getUser1() == loggedUser.getId()) {
                userAction.setUser1_action(action);
            } else {
                userAction.setUser2_action(action);
            }
            UserActionService.getImpl().updateUserAction(userAction);
        } else {
            UserActionService.getImpl().createUserAction(loggedUser.getId(), otherUserId, action, null);
        }

        response.sendRedirect("swipe-page.jsp");
    }
}
