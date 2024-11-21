package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.models.Message;
import gr.aueb.cf.mutu.models.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static gr.aueb.cf.mutu.models.Message.messages;

@WebServlet("/chat")
public class Chat extends HttpServlet {

//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        User loggedUser = Authentication.getSessionUser(request);
//
//        if (loggedUser == null) {
//            // If no user is logged in, redirect to the login page
////            response.sendRedirect("login.jsp");
//            return;
//        }
//
//        int matchId;
//        try {
//            matchId = Integer.parseInt(request.getParameter("match"));
//        }
//        catch (NumberFormatException e) {
//            response.setStatus(400);
//            return;
//        }
//
//        int since;
//        try {
//            since = Integer.parseInt(request.getParameter("since"));
//        }
//        catch (NumberFormatException e) {
//            response.setStatus(400);
//            return;
//        }
//
//        List<Message> messages = Message.getNewMessagesByUserIds(loggedUser.getId(), matchId, since);
//
//        response.setContentType("application/json");
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for (Message message : messages) {
//            sb.append(message.toJson());
//            sb.append(",");
//        }
//
//        if (messages.size() > 0) {
//            sb.deleteCharAt(sb.length()-1);
//        }
//        sb.append("]");
//
//        Writer writer = response.getWriter();
//        writer.write(sb.toString());
//    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User loggedUser = Authentication.getSessionUser(request);

        if (loggedUser == null) {
            // If no user is logged in, redirect to the login page
            response.sendRedirect("login.jsp");
            return;
        }

        int matchId;
        try {
            matchId = Integer.parseInt(request.getParameter("match"));
        }
        catch (NumberFormatException e) {
            response.sendRedirect("matches.jsp");
            return;
        }

        String messageTyped = request.getParameter("messageTyped");
        Message messageSent = new Message(loggedUser.getId(),matchId, messageTyped, System.currentTimeMillis() );
        messages.add(messageSent);

        response.setContentType("application/json");
        response.getWriter().write(messageSent.toJson());

    }
}
