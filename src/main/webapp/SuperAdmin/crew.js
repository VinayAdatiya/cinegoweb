function fetchAllCrewMembers() {
    $.ajax({
        url: `${CONFIG.baseURL}/getCrewMembers`,
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
            console.error('Error fetching crews:', error);
        }
    });
}

function renderTable(crews) {
    const tbody = document.querySelector("#crewTable tbody");
    tbody.innerHTML = "";

    crews.forEach((crew, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${crew.crewName}</td>
        `;
        tbody.appendChild(row);
    });
}

function openForm() {
    document.getElementById("crewPopup").style.display = "flex";
    document.getElementById("overlay").style.display = "block";
}

function closeCrewPopup() {
    document.getElementById("crewPopup").style.display = "none";
    document.getElementById("overlay").style.display = "none";
    document.getElementById("addCrewForm").reset();
}

document.getElementById("addCrewForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const crewName = document.getElementById("crewName").value;
    console.log(crewName);
    $.ajax({
        url: `${CONFIG.baseURL}/addCrew`,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({crewName}),
        xhrFields: {
            withCredentials: true
        },
        success: function () {
            closeCrewPopup();
            showPopupMessage("Crew added successfully!")
            fetchAllCrewMembers();
            clearForm();
        },
        error: function (xhr, status, error) {
            console.error("Error adding crew:", error);
            let response = JSON.parse(xhr.responseText);
            showMessage(response.message);
        }
    });
});

document.addEventListener("DOMContentLoaded", fetchAllCrewMembers);
