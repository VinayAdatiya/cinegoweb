function fetchCities() {
    $.ajax({
        url: `${CONFIG.baseURL}/fetchCities`,
        type: 'GET',
        dataType: 'json',
        credentials: 'include',
        success: function (result) {
            let cityDropdown = $('#city');
            cityDropdown.empty();
            cityDropdown.append('<option value="">Select City</option>');

            if (result.data.length === 0) {
                cityDropdown.append('<option value="">No Cities Available</option>');
                return;
            }

            $.each(result.data, function (index, city) {
                cityDropdown.append('<option value="' + city.cityId + '">' + city.cityName + '</option>');
            });
        },
        error: function (xhr, status, error) {
            console.error('Error fetching cities:', error);
        }
    });
}


function openForm() {
    document.getElementById("theaterModal").style.display = "block";
    fetchCities();
}

function closeForm() {
    document.getElementById("theaterModal").style.display = "none";
    clearForm();
}

function clearForm() {
    document.getElementById("theaterForm").reset();
}

function submitTheater() {
    const cityDropdown = document.getElementById("city");
    const cityId = cityDropdown.value;

    if (!cityId) {
        alert("Please select a city.");
        return;
    }

    const theaterData = {
        theaterAdmin: {
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        },
        theaterName: document.getElementById("theaterName").value,
        theaterRating: document.getElementById("theaterRating").value,
        theaterAddress: {
            addressLine1: document.getElementById("addressLine1").value,
            addressLine2: document.getElementById("addressLine2").value,
            pincode: document.getElementById("pincode").value,
            "city": {
                cityId: cityId
            }
        },
    };

    console.log("Submitting Data:", theaterData);

    fetch(`${CONFIG.baseURL}/addTheater`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(theaterData)
    })
        .then(response => {
            if (response.status === 200 || response.status === 201) {
                alert("Theater added successfully!");
                closeForm();
                clearForm();
                fetchAllTheaters();
            } else {
                return response.json().then(data => {
                    throw new Error(data.message || "Failed to add theater.");
                });
            }
        })
        .catch(error => {
            console.error("Error:", error);
            showMessage(error.message);
        });
}

function fetchAllTheaters() {
    $.ajax({
        url: `${CONFIG.baseURL}/getAllTheaters`,
        type: 'GET',
        dataType: 'json',
        credentials: 'include',
        success: function (result) {
            if (result && result.data) {
                renderTable(result.data);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error fetching theaters:', error);
        }
    });
}

function renderTable(theaters) {
    const tbody = document.querySelector("#theaterTable tbody");
    tbody.innerHTML = "";

    theaters.forEach((theater, index) => {
        const cityName = theater.theaterAddress?.city?.cityName || "N/A";
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${theater.theaterName}</td>
            <td>${theater.theaterRating}</td>
            <td>${cityName}</td>
            <td>
                <button class="view-btn" onclick='showDetails(${JSON.stringify(JSON.stringify(theater))})'>View</button>
                <button class="delete-btn" onclick='deleteTheater(${theater.theaterId})'>Delete</button>
            </td>
        `;

        tbody.appendChild(row);
    });
}

function deleteTheater(theaterId) {
    if (confirm("Are you sure you want to delete this theater?")) {
        $.ajax({
            url: `${CONFIG.baseURL}/deleteTheater?theaterId=${encodeURIComponent(theaterId)}`,
            type: 'DELETE',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                console.log("Delete result:", result);
                showPopupMessage("Theater deleted successfully.");
                fetchAllTheaters();
            },
            error: function (xhr, status, error) {
                console.error('Error fetching theaters:', error);
            }
        });
    }
}

function showDetails(theaterStr) {
    const theater = JSON.parse(theaterStr);
    const admin = theater.theaterAdmin || {};
    const address = theater.theaterAddress || {};
    const city = address.city || {};
    const state = city.state || {};

    const content = `
            Theater Name: ${theater.theaterName}
            Rating: ${theater.theaterRating}

            ----- Theater Admin Info -----
            Name: ${admin.firstName} ${admin.lastName}
            EmailId: ${admin.email}
            Contact: ${admin.phoneNumber}

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

document.addEventListener("DOMContentLoaded", fetchAllTheaters);