<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp"/>
    <title>Create Account</title>
    <style>

    </style>
</head>

<body>

<div class="container">

    <div class="card shadow-lg my-3">
        <div class="card-header">
            <span><strong>Create Account</strong></span>
        </div>

        <div class="card-body">
            <form method="POST" action="create-account" class="d-flex flex-column">
                <div class="mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input class="form-control" type="email" id="email" name="email" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="password">Password</label>
                    <input class="form-control" type="password" id="password" name="password" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="name">Name</label>
                    <input class="form-control" type="text" id="name" name="name" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="birthday">Birthday</label>
                    <input class="form-control" type="date" id="birthday" name="birthday"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="height">Height (cm)</label>
                    <input class="form-control" type="number" id="height" name="height"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="weight">Weight (kg)</label>
                    <input class="form-control" type="number" id="weight" name="weight"/>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="bio">Bio</label>
                    <textarea class="form-control"
                              id="bio"
                              name="bio"
                              rows="4"
                              placeholder="Tell us about yourself..."
                    ></textarea>
                </div>

                <button class="btn btn-outline-secondary align-self-end" id="createAccountButton" type="submit">Create Account</button>
            </form>
        </div>
    </div>

</div>
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