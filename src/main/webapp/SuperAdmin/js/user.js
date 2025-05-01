function fetchAllUsers() {
    $.ajax({
        url: `${CONFIG.baseURL}/getAllUsers`,
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
            console.error('Error fetching users:', error);
        }
    });
}

function renderTable(users) {
    const tbody = document.querySelector("#userTable tbody");
    tbody.innerHTML = "";

    users.forEach((user, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${user.firstName} ${user.lastName}</td>
            <td>${user.role}</td>
            <td>${user.phoneNumber}</td>
            <td>
                <button onclick='showDetails(${JSON.stringify(JSON.stringify(user))})'>
                    View
                </button>
            </td>
        `;

        tbody.appendChild(row);
    });
}

function showDetails(userStr) {
    const user = JSON.parse(userStr);
    const address = user.address || {};
    const city = address.city || {};
    const state = city.state || {};

    const content = `

            ----- Personal Info -----
            Username: ${user.userName}
            Name: ${user.firstName} ${user.lastName}
            EmailId: ${user.email}
            Contact: ${user.phoneNumber}
            Role: ${user.role}

            ----- Address Info -----
            ${address.addressLine1}, ${address.addressLine2}
            ${city.cityName}, ${address.pincode}
            ${state.stateName} (${state.stateCode})
        `;

    document.getElementById("popup-content").textContent = content;
    document.getElementById("popup").style.display = "block";
    document.getElementById("overlay").style.display = "block";
}

function closePopup() {
    document.getElementById("popup").style.display = "none";
    document.getElementById("overlay").style.display = "none";
}

document.addEventListener("DOMContentLoaded", fetchAllUsers);