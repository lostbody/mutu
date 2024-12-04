<%@ page import="gr.aueb.cf.mutu.models_dev.User" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.models_dev.Message" %>
<%@ page import="gr.aueb.cf.mutu.models_dev.Picture" %>
<%
    User loggedUser = Authentication.getSessionUser(request);
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

    User match = User.getById(matchId);

    List<Message> messages = Message.getConversationByUserIds(loggedUser.getId(), matchId);
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
                src="<%= Picture.getAvatarByUserId(matchId) %>"
                alt="<%= match.getName() %>"
                title="<%= match.getName() %>"
            />

            <div class="card-title"><span><%= match.getName() %></span></div>
        </div>

        <div class="d-flex w-100 flex-column align-items-start p-3 pt-0">
            <% for (Message message : messages) {
                String style = loggedUser.getId() == message.getUser1() ? "bg-primary align-self-end" : "bg-secondary";
            %>
                <div class="message rounded-pill px-3 py-2 text-white my-1 <%= style %>">
                    <span><%= message.getContent() %></span>
                </div>
            <% } %>
        </div>

        <form class="d-flex p-2">
            <input class="form-control" type="text" id="messageTyped"/>
            <button class="btn" type="button" id="sendButton">Send</button>
        </form>
    </div>
</div>
</body>

<script>

    const matchId = <%= matchId %>;

    const messageTyped$ = document.getElementById("messageTyped")
    const sendButton$ = document.getElementById("sendButton")
    const conversation$ = document.querySelector(".conversation")

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
                    const side = serverMessage.sender === matchId ? "justify-content-start" : "justify-content-end"
                    messageDiv$.className = "d-flex" + side + "mb-2"
                    console.log(serverMessage, matchId, side)
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
