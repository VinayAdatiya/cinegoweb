function loadSidebar() {
    const sidebarHTML = `
        <div class="sidebar">
            <h2>Theater Admin</h2>
            <a href="theater-admin-home.html" class="tab">Dashboard</a>
            <a href="movies.html" class="tab">Movies</a>
            <a href="show.html" class="tab">Shows</a>
            <a href="screen.html" class="tab">Screens</a>
            <a href="my-theater.html" class="tab">My Theater</a>
         
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
                    window.location.href = "../login.html";
                },
                error: function (xhr) {
                    console.log(xhr.message);
                    alert("Logout failed!");
                    console.error(xhr);
                }
            });
        });

        // Fetch current user details
        $.ajax({
            type: "GET",
            url: `${CONFIG.baseURL}/getCurrentUser`,
            xhrFields: {withCredentials: true},
            contentType: "application/json",
            success: function (response) {
                console.log("getCurrentUser response:", response.data);
                window.currentUser = response.data;
                const userDisplay = document.getElementById('user-display');
                if (userDisplay) {
                    userDisplay.textContent = `${window.currentUser.firstName} ${window.currentUser.lastName}`;
                }
            },
            error: function (xhr) {
                let response = JSON.parse(xhr.responseText);
                alert(response.message);
                console.log("No User Found !!!");
            }
        });
    } else {
        console.error("Sidebar container not found!");
    }
}

document.addEventListener("DOMContentLoaded", loadSidebar);
