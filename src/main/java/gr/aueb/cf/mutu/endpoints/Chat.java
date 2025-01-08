package gr.aueb.cf.mutu.endpoints;

import gr.aueb.cf.mutu.Authentication;
import gr.aueb.cf.mutu.dto.MessageDto;
import gr.aueb.cf.mutu.service.MessageService;
import gr.aueb.cf.mutu.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("/chat")
public class Chat extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto loggedUser = Authentication.getSessionUser(request);
        if (loggedUser == null) {
            response.setStatus(401);
            return;
        }

        int matchId;
        try {
            matchId = Integer.parseInt(request.getParameter("match"));
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        long since;
        try {
            since = Long.parseLong(request.getParameter("since"));
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        List<MessageDto> messages = MessageService.getImpl().getNewMessagesByUserIds(loggedUser.getId(), matchId, since);

        response.setContentType("application/json");

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (MessageDto message : messages) {
            sb.append(message.toJson());
            sb.append(",");
        }

        if (messages.size() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("]");

        Writer writer = response.getWriter();
        writer.write(sb.toString());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto loggedUser = Authentication.getSessionUser(request);
        if (loggedUser == null) {
            response.setStatus(401);
            return;
        }

        int matchId;
        try {
            matchId = Integer.parseInt(request.getParameter("match"));
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        String messageTyped = request.getParameter("messageTyped");
        MessageDto messageSent = MessageService.getImpl().createMessage(loggedUser.getId(),matchId, messageTyped, System.currentTimeMillis());

        response.setContentType("application/json");
        response.getWriter().write(messageSent.toJson());
    }
}
