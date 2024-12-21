<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.dto.UserDto" %>
<%@ page import="gr.aueb.cf.mutu.service.UserActionService" %>
<%@ page import="gr.aueb.cf.mutu.service.PictureService" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    UserDto loggedUser = Authentication.getSessionUser(request);
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<UserDto> matches = UserActionService.getImpl().getMatchesByUserId(loggedUser.getId());
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
        <% for (UserDto match : matches) {
            String avatar = PictureService.getImpl().getAvatarByUserId(match.getId());
            if (avatar == null) { avatar = "static/img/no-profile-pic.jpg"; }
        %>
            <div class="border rounded p-2">
                <a href="chat.jsp?match=<%= match.getId() %>">
                    <div class="d-flex gap-3 align-items-center">
                        <div>
                            <img
                                    class="avatar rounded-circle"
                                    src="data:image/jpeg;base64,<%= avatar %>"
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
