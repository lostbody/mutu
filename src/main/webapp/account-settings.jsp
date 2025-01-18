<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="gr.aueb.cf.mutu.Authentication" %>
<%@ page import="gr.aueb.cf.mutu.dto.UserDto" %>
<%@ page import="gr.aueb.cf.mutu.dto.InterestDto" %>
<%@ page import="java.util.List" %>
<%@ page import="gr.aueb.cf.mutu.service.InterestService" %>
<%@ page import="gr.aueb.cf.mutu.dto.PictureDto" %>
<%@ page import="gr.aueb.cf.mutu.service.PictureService" %>
<%
    UserDto loggedUser = Authentication.getSessionUser(request);
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    boolean updateSuccess = request.getParameter("updateSuccess") != null;

    List<InterestDto> interests = InterestService.getImpl().getAll();
    List<InterestDto> userInterests = InterestService.getImpl().getInterestsByUserId(loggedUser.getId());
    List<PictureDto> pictures = PictureService.getImpl().getPicturesByUserId(loggedUser.getId());
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="head.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">
    <title>Account Settings</title>

    <style>
        .selected {
            color: white;
            background-color: #0056b3;
            border-color: white;
        }

        .img-container {
            width: 33%;
            height: 200px;
            overflow: hidden;
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
        }

        .img-standard {
            width: 100%;
            height: 100%;
            object-fit: cover;
            object-position: center;
        }

        .image-controls {
            position: absolute;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 10px;
        }

        .image-controls .btn {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: rgba(255, 255, 255, 0.8);
            border: none;
            opacity: 0;
            transition: opacity 0.2s;
        }

        .delete-btn {
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .img-container:hover .btn {
            opacity: 1;
        }

    </style>
</head>

<body>

<div class="container">

    <jsp:include page="navbar.jsp"/>
    <div class="mt-3">
        <span><strong>Account Settings</strong></span>
    </div>

    <% if (updateSuccess) { %>
    <span style="color: green;">Account updated successfully!</span>
    <% } %>

    <div class="mb-3">
        <label class="form-label">Photos:</label>
        <div id="pictures" class="d-flex flex-wrap"></div>
    </div>

    <div class="mb-3">
        <label class="form-label" for="fileInput">Upload a new picture:</label>
        <div class="d-flex gap-3">
            <input id="fileInput" type="file" class="form-control" name="pictureFile" accept="image/jpeg" required>
            <button id="upload" class="btn btn-outline-secondary">Upload</button>
        </div>
    </div>

    <div class="mb-3">
        <label class="form-label" for="weight">Weight (kg):</label>
        <input class="form-control" type="number" id="weight" name="weight" value="<%=loggedUser.getWeight()%>"/>
    </div>

    <div class="mb-3">
        <label class="form-label" for="bio">Bio:</label>
        <textarea class="form-control"
                  id="bio"
                  name="bio"
                  rows="4"
                  placeholder="Tell us about yourself..."><%=loggedUser.getBio() %></textarea>
    </div>

    <div class="mb-3">
        <label class="form-label">Interests:</label>
        <div class="d-flex flex-wrap gap-1">
            <% for (InterestDto interest : interests) {
                boolean isSelected = userInterests.stream().anyMatch(x -> x.getId() == interest.getId());
                String selected = isSelected ? "selected" : "";
            %>
            <div class="interest p-1 border rounded <%= selected %>"
                 data-id="<%= interest.getId() %>"><%= interest.getName() %>
            </div>
            <% } %>
        </div>
    </div>
</div>
</body>

<script>

    const weight$ = document.getElementById("weight")
    const bio$ = document.getElementById("bio")

    const inputs = [weight$, bio$]

    const interests = Array.from(document.querySelectorAll(".interest"))
    const images = Array.from(document.querySelectorAll(".photo"))


    const updateUser = () => {
        const params = {
            weight: weight$.value,
            bio: bio$.value,
            interests: interests
                .filter((interest$) => interest$.classList.contains("selected"))
                .map((interest$) => interest$.dataset.id)
        }
        const formBody = new URLSearchParams(params).toString()

        fetch("account-settings", {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formBody,
        })
        .then(response => {
            if (response.ok) {
                console.log('Update successful');
            } else {
                console.error('Update failed with status:', response.status);
            }
        })
        .catch(error => {
             console.error('Error:', error);
        });
    }

    for (const input$ of inputs) {
        input$.addEventListener("input", updateUser)
    }
    for (const interest$ of interests) {
        interest$.addEventListener("click", () => {
            interest$.classList.toggle("selected")
            updateUser()
        })
    }


    const deletePicture = (picture) => {
        picture.element$.remove()

        const params = {
            id: picture.id,
        }
        const query = new URLSearchParams(params).toString()

        fetch("pictures?" + query, {
            method: "DELETE",
        })
    }

    const movePicture = (picture, direction) => {
        const pictureIndex = pictures.indexOf(picture)
        const otherIndex = direction === "left" ? pictureIndex - 1 : pictureIndex + 1
        if (otherIndex === -1 || otherIndex === pictures.length) {
            return
        }

        if (direction === "left") {
            pictures$.insertBefore(pictures[pictureIndex].element$, pictures[otherIndex].element$)
        } else {
            pictures$.insertBefore(pictures[otherIndex].element$, pictures[pictureIndex].element$)
        }

        const temp = pictures[pictureIndex]
        pictures[pictureIndex] = pictures[otherIndex]
        pictures[otherIndex] = temp

        const params = {
            id: picture.id,
            direction: direction,
        }
        const query = new URLSearchParams(params).toString()

        fetch("pictures?" + query, {
            method: "PUT",
        })
    }

    const pictures$ = document.getElementById("pictures")
    const pictures = [
        <% for (PictureDto pictureDto : pictures) { %>
        {
            id: <%= pictureDto.getId() %>,
            data: "<%= pictureDto.getBlob() %>",
            filename: "<%= pictureDto.getFilename() %>",
            element$: null,
        },
        <% } %>
    ]

    const createPictureElement = (picture) => {
        const container$ = document.createElement("div")
        container$.classList.add("card", "shadow-sm", "img-container")
        pictures$.appendChild(container$)

        const image$ = document.createElement("img")
        image$.classList.add("img-fluid", "img-standard")
        image$.setAttribute("src", "data:image/jpeg;base64," + picture.data)
        image$.setAttribute("alt", picture.filename)
        container$.appendChild(image$)

        const controls$ = document.createElement("div")
        controls$.classList.add("image-controls")
        container$.appendChild(controls$)

        const buttonDelete$ = document.createElement("div")
        buttonDelete$.classList.add("btn", "btn-outline-secondary", "delete-btn")
        buttonDelete$.innerHTML = '<i class="fa-solid fa-times"></i>'
        buttonDelete$.addEventListener("click", () => deletePicture(picture))
        container$.appendChild(buttonDelete$)

        const buttonLeft$ = document.createElement("div")
        buttonLeft$.classList.add("btn", "btn-outline-secondary")
        buttonLeft$.innerHTML = '<i class="fa-solid fa-chevron-left"></i>'
        buttonLeft$.addEventListener("click", () => movePicture(picture, "left"))
        controls$.appendChild(buttonLeft$)

        const buttonRight$ = document.createElement("div")
        buttonRight$.classList.add("btn", "btn-outline-secondary")
        buttonRight$.innerHTML = '<i class="fa-solid fa-chevron-right"></i>';
        buttonRight$.addEventListener("click", () => movePicture(picture, "right"))
        controls$.appendChild(buttonRight$)

        return container$
    }

    for (const picture of pictures) {
        picture.element$ = createPictureElement(picture)
    }


    const uploadPicture = () => {
        const file = fileInput$.files[0]
        const filename = file.name

        const reader = new FileReader()
        reader.onload = () => {
            const imageData = reader.result.slice("data:image/jpeg;base64,".length)

            const params = {
                filename: filename,
                imageData: imageData,
            }
            const formBody = new URLSearchParams(params).toString()

            fetch("pictures", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formBody,
            })
                .then((result) => result.json())
                .then((createdPicture) => {
                    const picture = {
                        id: createdPicture.id,
                        data: imageData,
                        filename: filename,
                        element$: null,
                    }
                    picture.element$ = createPictureElement(picture)
                    pictures.push(picture)
                })
        }
        reader.readAsDataURL(file)
    }

    const uploadButton$ = document.getElementById("upload")
    const fileInput$ = document.getElementById("fileInput")

    uploadButton$.addEventListener("click", uploadPicture)
</script>
</html>