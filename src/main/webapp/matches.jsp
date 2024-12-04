<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="gr.aueb.cf.mutu.models_dev.User" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.models_dev.UserAction" %>
<%@ page import="gr.aueb.cf.mutu.models_dev.Picture" %>
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
    <jsp:include page="head.jsp"/>
    <title>Matches</title>
    <style>
        .avatar {
            width: 50px;
            height: 50px;
        }
    </style>
</head>
<body>

<div class="container">
    <jsp:include page="navbar.jsp" />

    <div class="mt-3">
        <span><strong>Matches</strong></span>
    </div>

    <div class="d-flex gap-3 flex-column mt-3">
        <% for (User match : matches) { %>
            <div class="border rounded p-2">
                <a href="chat.jsp?match=<%= match.getId() %>">
                    <div class="d-flex gap-3 align-items-center">
                        <div>
                            <img
                                    class="avatar rounded-circle"
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
