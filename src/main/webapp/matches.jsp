<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="gr.aueb.cf.mutu.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.models.UserAction" %>
<%@ page import="gr.aueb.cf.mutu.models.Picture" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User loggedUser = Authentication.getSessionUser(request);
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<User> matches = UserAction.getMatchesByUserId(loggedUser.getId());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Matches</title>
    <style>
        /* add container class with width 400 and centered */

        .avatar {
            width: 50px;
            height: 50px;
            border-radius: 25px;
        }

        .match {
            border: 1px solid black;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<!-- turn inline css to classes -->
<div>
    <jsp:include page="navbar.jsp" />

    <div style="margin-bottom: 10px">
        <span><strong>Matches</strong></span>
        <!-- remove strong tag -->
    </div>

    <div class="matches">
        <% for (User match : matches) { %>
            <div class="match">
                <a href="chat.jsp?match=<%= match.getId() %>">
                    <div style="display: flex; align-items: center; gap: 15px">
                        <div>
                            <img
                                    class="avatar"
                                    src="<%= Picture.getAvatarByUserId(match.getId()) %>"
                                    alt="<%= match.getName() %>"
                                    title="<%= match.getName() %>"
                            />
                        </div>
                        <div><span><%= match.getName() %></span></div>
                    </div>
                </a>
            </div>
        <% } %>
    </div>
</div>
</body>
</html>
