function loadSidebar() {
    const sidebarItems = [
        {label: "Dashboard", href: "theater-admin-home.html"},
        {label: "Movies", href: "add-show.html"},
        {label: "Shows", href: "show.html"},
        {label: "Screens", href: "screen.html"},
        {label: "My Theater", href: "my-theater.html"}
    ];

    const sidebarContainer = document.getElementById("sidebar-container");

    if (!sidebarContainer) {
        console.error("Sidebar container not found!");
        return;
    }

    let sidebarHTML = `
        <div class="sidebar">
            <h2>Theater Admin</h2>
            ${sidebarItems.map(item => `<a href="${item.href}" class="tab">${item.label}</a>`).join('')}
        </div>
    `;

    sidebarContainer.innerHTML = sidebarHTML;

    const currentPage = window.location.pathname.split('/').pop();

    document.querySelectorAll('.sidebar .tab').forEach(link => {
        if (link.getAttribute('href') === currentPage) {
            link.classList.add('active');
        }
    });
}

document.addEventListener("DOMContentLoaded", loadSidebar);