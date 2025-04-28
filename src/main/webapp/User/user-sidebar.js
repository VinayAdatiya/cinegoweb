function loadUserSidebar() {
    const sidebarHTML = `
        <div class="sidebar">
            <h2>User</h2>
            <a href="user-home.html" class="tab">Dashboard</a>
            <a href="BookTickets.html" class="tab">Book Tickets</a>
            <a href="MyBookings.html" class="tab">My Bookings</a>
            <a href="Profile.html" class="tab">Profile</a>
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
    } else {
        console.error("Sidebar container not found!");
    }
}

document.addEventListener("DOMContentLoaded", loadUserSidebar);
