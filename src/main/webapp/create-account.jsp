<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Create Account</title>
    <link
            href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
            rel="stylesheet"
    />
    <style>
        body {
            font-family: "Roboto", "Helvetica", "Arial", sans-serif;
            margin: 20px;
        }

        form {
            max-width: 400px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: orange;
        }

        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="number"],
        input[type="date"],
        textarea {
            width: 400px;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        textarea {
            resize: vertical;
        }

        button {
            padding: 10px 15px;
            background-color: #4caf50;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 3px;
        }

        button:hover {
            background-color: #45a049;
        }

    </style>
</head>

<body>
<h2>Create Account</h2>

<label for="email">Email:</label>
<input type="email" id="email" name="email" required/>

<label for="password">Password:</label>
<input type="password" id="password" name="password" required/>

<label for="name">Name:</label>
<input type="text" id="name" name="name" required/>

<label for="birthday">Birthday:</label>
<input type="date" id="birthday" name="birthday"/>

<label for="height">Height (cm):</label>
<input type="number" id="height" name="height"/>

<label for="weight">Weight (kg):</label>
<input type="number" id="weight" name="weight"/>

<label for="bio">Bio:</label>
<textarea
        id="bio"
        name="bio"
        rows="4"
        placeholder="Tell us about yourself..."
></textarea>

<button id="createAccountButton" type="submit">Create Account</button>
</body>

<script>

    const email$ = document.getElementById("email")
    const password$ = document.getElementById("password")
    const name$ = document.getElementById("name")
    const birthday$ = document.getElementById("birthday")
    const height$ = document.getElementById("height")
    const weight$ = document.getElementById("weight")
    const bio$ = document.getElementById("bio")
    const button$ = document.getElementById("createAccountButton")

    const elements = [email$, password$, name$, birthday$, height$, weight$, bio$]

    function handleAccountCreation() {
        const params = {
            email: email$.value,
            password: password$.value,
            name: name$.value,
            birthday: birthday$.value,
            height: height$.value,
            weight: weight$.value,
            bio: bio$.value
        }
        const formBody = new URLSearchParams(params).toString()

        fetch("create-account", {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formBody
        })
            .then(function (response) {
                return response.json()
            })
            .then(function (data) {
                if (data.error) {
                    showError(data.error)
                } else if (data.redirect) {
                    window.location.href = data.redirect;
                    ////
                }
            })
    }

    function showError(message) {
        let errorElement = document.getElementById("error-message");
        if (!errorElement) {
            errorElement = document.createElement("span");
            errorElement.id = "error-message";
            errorElement.style.color = "red";
            document.body.insertBefore(errorElement, document.body.firstChild);
        }
        errorElement.textContent = message;
    }

    button$.addEventListener("click", handleAccountCreation)

</script>
</html>