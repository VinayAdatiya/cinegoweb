function loadSidebar() {
    const sidebarHTML = `
        <div class="sidebar">
            <h2>SuperAdmin</h2>
            <a href="./super-admin-home.html" class="tab">Dashboard</a>
            <a href="./user.html" class="tab">User</a>
            <a href="./crew.html" class="tab">Crew</a>
            <a href="./theater-admin.html" class="tab">Theater Admin</a>
            <a href="./theater.html" class="tab">Theater</a>
            <a href="./movie.html" class="tab">Movie</a>
         
            <div class="sidebar-footer">
            <p id="user-display" style="font-size: 14px; color: #ccc; margin: -10px 0 10px 0;">Loading user...</p>
            <button id="logout-btn" title="Logout">Logout</button>
            </div>    
        </div>
    `;

    const sidebarContainer = document.getElementById("sidebar-container");
    if (sidebarContainer) {
        sidebarContainer.innerHTML = sidebarHTML;

        let currentPage = window.location.pathname.split('/').pop();
        document.querySelectorAll('.sidebar .tab').forEach(link => {
            if (link.getAttribute('href') === currentPage) {
                link.classList.add('active');
            }
        });

        document.getElementById('logout-btn').addEventListener('click', function () {
            $.ajax({
                type: "GET",
                url: `${CONFIG.baseURL}/logout`,
                xhrFields: {withCredentials: true},
                success: function () {
                    localStorage.clear();
                    window.location.href = "../Login.html";
                },
                error: function (xhr) {
                    console.log(xhr.messageerror);
                    alert("Logout failed!");
                    console.error(xhr);
                }
            });
        });

    } else {
        console.error("Sidebar container not found!");
    }
}

document.addEventListener("DOMContentLoaded", loadSidebar);

$(document).ready(function () {
    let user = null;
    $.ajax({
        type: "GET",
        url: `${CONFIG.baseURL}/getCurrentUser`,
        xhrFields: {
            withCredentials: true
        },
        contentType: "application/json",
        success: function (response) {
            console.log("getCurrentUser response:", response.data);
            window.currentUser = response.data;
            const userDisplay = document.getElementById('user-display');
            if (userDisplay) {
                userDisplay.textContent = `${window.currentUser.firstName} ${window.currentUser.lastName} `;
            }
        },
        error: function (xhr) {
            let response = JSON.parse(xhr.responseText);
            alert(response.message);
            console.log("No User Found !!!");
        }
    });
})