package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dto.UserDto;
import gr.aueb.cf.mutu.service.UserInterestService;
import gr.aueb.cf.mutu.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/account-settings")
public class AccountSettings extends HttpServlet {

    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        UserDto loggedUser = Authentication.getSessionUser(request);
        if (loggedUser == null) {
            response.setStatus(401);
            return;
        }

        // Retrieve updated information from the form
        String password = request.getParameter("password");
        String weightStr = request.getParameter("weight");
        String bio = request.getParameter("bio");
        String interestsStr = request.getParameter("interests");

        int weight;
        try {
            weight = Integer.parseInt(weightStr);
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        Set<Long> interestIds;
        try {
            interestIds = Arrays.stream(interestsStr.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toSet());
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }


        // Update the logged user's information with the values we get from the form
        // which are retrieved from the request.getParameter... (see above)
        loggedUser.setPassword(password);
        loggedUser.setWeight(weight);
        loggedUser.setBio(bio);
        UserService.getImpl().updateUser(loggedUser);

        UserInterestService.getImpl().setUserInterests(loggedUser.getId(), interestIds);
    }

}


