<%@ page import="gr.aueb.cf.mutu.models.User" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.models.Message" %>
<%@ page import="gr.aueb.cf.mutu.models.Picture" %>
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
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Chat</title>
    <style>
        body {
            font-family: sans-serif;
            font-size: 14px;
        }

        .container {
            width: 400px;
            margin: 0 auto;
        }

        .header {
            display: flex;
            align-items: center;
            gap: 5px;
            margin-bottom: 5px;

            font-size: 1.3em;
            font-weight: bold;
        }

        .avatar {
            width: 50px;
            height: 50px;
            border-radius: 25px;
        }

        .conversation {
            width: 400px;
            padding: 10px; /* Add padding inside the conversation container */
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .conversation .message {
            width: 300px;
            overflow-wrap: break-word;
            border: 1px solid;
            border-radius: 25px;
            padding: 10px;
        }

        .conversation .message.left {
            align-self: flex-start;
            background-color: #f9f9f9;
        }

        .conversation .message.right {
            align-self: flex-end;
            background-color: #e0f7fa;
        }

        .submit form {
            width: 100%;
            display: flex;
            justify-content: end;
            gap: 5px;
        }

        .submit input {
            flex-grow: 1;
            background-color: #e0f7fa;
        }
    </style>
</head>
<body>
<div class="container">

    <jsp:include page="navbar.jsp"/>

    <div class="header">
        <img
                class="avatar"
                src="<%= Picture.getAvatarByUserId(matchId) %>"
                alt="<%= match.getName() %>"
                title="<%= match.getName() %>"
        />

        <div><span><%= match.getName() %></span></div>
    </div>

    <div class="conversation">
        <% for (Message message : messages) { %>
        <div class="message <%= loggedUser.getId() == message.getUser1() ? "right" : "left" %>">
            <span><%= message.getContent() %></span>
        </div>
        <% } %>
    </div>

    <div class="submit">
        <label>
            <input type="text" id="messageTyped" name="messageTyped"/>
        </label>
        <button class="send" type="submit" id="sendButton">Send</button>
    </div>
</div>
</body>

<script>

    const messageTyped$ = document.getElementById("messageTyped")
    const sendButton$ = document.getElementById("sendButton")
    const conversation$ = document.querySelector(".conversation")

    function handleSendMessage() {

        const params = {
            match: <%= matchId %>,
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
            .then(response => {
                return response.json()
            })
            .then(serverMessage => {
                console.log("Message sent:", serverMessage)

                const messageDiv = document.createElement("div")
                messageDiv.className = "message right"
                messageDiv.textContent = serverMessage.content
                conversation$.append(messageDiv)

            })
    }

    sendButton$.addEventListener("click", handleSendMessage)

    function getNewMessages() {

        let latestTimestamp = 0;

        const params = {
            match: <%= matchId %>,
            since: latestTimestamp,
        }
        const queryString = new URLSearchParams(params).toString()

        fetch("chat?" + queryString)
            .then(function (response) {
                return response.json()
            })
            .then(function (messages) {
                console.log(messages)

                if (messages.length > 0) {
                    latestTimestamp = messages[messages.length - 1].timestamp;
                }
            })
    }

    setInterval(getNewMessages, 1000);

</script>

</html>
