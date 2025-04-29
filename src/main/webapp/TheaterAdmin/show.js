document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("addShowModal");
    const iframe = document.getElementById("addShowIframe");

    document.getElementById("openFormBtn").addEventListener("click", () => {
        iframe.src = "add-show.html";
        modal.style.display = "block";
    });

    document.querySelector(".close").addEventListener("click", () => {
        iframe.src = "";
        modal.style.display = "none";
    });
});

window.onload = function () {
    fetchAllShows();
};

function fetchAllShows() {
    const theaterId = localStorage.getItem('theaterId');
    $.ajax({
        url: `${CONFIG.baseURL}/getShowByTheater?theaterId=${encodeURIComponent(theaterId)}`,
        type: 'GET',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result && result.data) {
                renderTable(result.data);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error fetching movies:', error);
        }
    });
}


function renderTable(shows) {
    const tbody = document.getElementById("showBody");
    let rows = "";

    shows.forEach((show, index) => {
        console.log(show);
        const showDate = show.showDate.join('-');
        const showTime = show.showTime.join(':');
        rows += `
            <tr>
                <td>${index + 1}</td>
                <td align="center">${show.movie.movieTitle}</td>
                <td align="center">${show.movie.movieRating}</td>
                <td align="center">${show.screen.screenTitle}</td>
                <td align="center">${show.screen.screenType.screenType}</td>
                <td align="center">${showDate}</td>
                <td align="center">${showTime}</td>
                <td align="center">
                 <button class="action-btn delete-btn" data-id="${show.showId}">Delete</button>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = rows;
    bindDeleteButton();
}

function bindDeleteButton() {
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            const showId = this.getAttribute('data-id');
            deleteShow(showId);
        });
    });
}

function deleteShow(showId) {
    if (confirm("Are you sure you want to delete this show?")) {
        $.ajax({
            url: `${CONFIG.baseURL}/deleteShow?showId=${encodeURIComponent(showId)}`,
            type: 'DELETE',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                console.log("Delete result:", result);
                showPopupMessage("Show deleted successfully.");
                fetchAllShows();
            },
            error: function (xhr, status, error) {
                let response = JSON.parse(xhr.responseText);
                showPopupMessage(response.message);
            }
        });
    }
}