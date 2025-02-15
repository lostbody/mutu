<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.dto.UserDto" %>
<%@ page import="gr.aueb.cf.mutu.dto.MessageDto" %>
<%@ page import="gr.aueb.cf.mutu.service.MessageService" %>
<%@ page import="gr.aueb.cf.mutu.service.PictureService" %>
<%@ page import="gr.aueb.cf.mutu.service.UserService" %>
<%
    UserDto loggedUser = Authentication.getSessionUser(request);
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int matchId;
    try {
        matchId = Integer.parseInt(request.getParameter("match"));
    } catch (NumberFormatException e) {
        response.sendRedirect("matches.jsp");
        return;
    }

    UserDto match = UserService.getImpl().getById(matchId);
    String avatar = PictureService.getImpl().getAvatarByUserId(matchId);
    if (avatar == null) { avatar = "static/img/no-profile-pic.jpg"; }
    List<MessageDto> messages = MessageService.getImpl().getConversationByUserIds(loggedUser.getId(), matchId);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp"/>
    <title>Chat</title>
    <style>
        .avatar {
            width: 50px;
            height: 50px;
        }
    </style>
</head>
<body>
<div class="container">

    <jsp:include page="navbar.jsp"/>

    <div class="card mt-3 shadow-sm">
        <div class="card-body d-flex align-items-center gap-3">
            <img
                class="avatar rounded-circle"
                src="data:image/jpeg;base64,<%= avatar %>"
                alt="<%= match.getName() %>"
                title="<%= match.getName() %>"
            />

            <div class="card-title"><span><%= match.getName() %></span></div>
        </div>

        <div id="conversation" class="d-flex w-100 flex-column align-items-start p-3 pt-0">
            <% for (MessageDto message : messages) {
                String style = loggedUser.getId() == message.getUser1() ? "bg-primary align-self-end" : "bg-secondary";
            %>
                <div class="message rounded-pill px-3 py-2 text-white my-1 <%= style %>">
                    <span><%= message.getContent() %></span>
                </div>
            <% } %>
        </div>

        <div class="d-flex p-2 align-self-end">
            <input class="div-control" type="text" id="messageTyped"/>
            <button class="btn" type="button" id="sendButton">Send</button>
        </div>
    </div>
</div>
</body>

<script>

    const matchId = <%= matchId %>;

    const messageTyped$ = document.getElementById("messageTyped")
    const sendButton$ = document.getElementById("sendButton")
    const conversation$ = document.getElementById("conversation")

    const handleSendMessage = () => {
        const params = {
            match: matchId,
            messageTyped: messageTyped$.value
        }
        const formBody = new URLSearchParams(params).toString()

        fetch("chat", {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formBody,
        })
            .then((response) => response.json())
            .then((serverMessage) => {
                console.log("MessageDto sent:", serverMessage)
            })

        messageTyped$.value = ""
    }

    sendButton$.addEventListener("click", handleSendMessage)

    let latestTimestamp = Date.now();

    const getNewMessages = () => {
        const params = {
            match: matchId,
            since: latestTimestamp,
        }
        const queryString = new URLSearchParams(params).toString()

        fetch("chat?" + queryString)
            .then((response) => response.json())
            .then((messages) => {
                console.log(messages)

                for (const serverMessage of messages) {
                    const messageDiv$ = document.createElement("div")
                    const side = serverMessage.sender === matchId ? "bg-secondary" : "bg-primary align-self-end"
                    messageDiv$.className = "message rounded-pill px-3 py-2 text-white my-1 " + side
                    conversation$.append(messageDiv$)

                    const messageSpan$ = document.createElement("span")
                    messageSpan$.textContent = serverMessage.content
                    messageDiv$.append(messageSpan$)
                }

                if (messages.length > 0) {
                    latestTimestamp = messages[messages.length - 1].timestamp
                }
            })
    }

    setInterval(getNewMessages, 1000);

</script>

</html>
