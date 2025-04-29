async function populateDropdown(url, selectElement, idField, nameField) {
    const placeholderOption = document.createElement('option');
    placeholderOption.value = '';
    placeholderOption.textContent = 'Select';
    selectElement.appendChild(placeholderOption);

    const res = await fetch(url, {
        credentials: 'include'
    });
    const result = await res.json();
    const data = result.data;

    data.forEach(item => {
        const option = document.createElement('option');
        option.value = item[idField];
        option.textContent = item[nameField];
        selectElement.appendChild(option);
    });
}

document.getElementById('screenSelect').addEventListener('change', function () {
    const selectedScreenId = this.value;
    localStorage.setItem('selectedScreenId', selectedScreenId);
    populateSeatCategories(selectedScreenId);
});

async function populateSeatCategories(screenId) {
    const res = await fetch(`${CONFIG.baseURL}/getSeatCategoryByScreen?screenId=${encodeURIComponent(screenId)}`);
    const result = await res.json();
    const categories = result.data;
    const seatPricesContainer = document.getElementById('seatPrices');

    seatPricesContainer.style.display = 'flex';
    seatPricesContainer.style.flexWrap = 'wrap';
    seatPricesContainer.style.justifyContent = 'space-between';

    categories.forEach(seat => {
        const div = document.createElement('div');
        div.className = 'seat-category';
        div.style.marginRight = '10px';
        div.innerHTML = `
          <label>${seat.seatType} : </label>
          <input type="number" min="0" placeholder="Enter price" data-seat-id="${seat.seatCategoryId}" required>
        `;
        seatPricesContainer.appendChild(div);
    });
}


const theaterId = localStorage.getItem('theaterId');
document.addEventListener('DOMContentLoaded', async () => {
    await populateDropdown(`${CONFIG.baseURL}/getAllMovies`, document.getElementById('movieSelect'), 'movieId', 'movieTitle');
    await populateDropdown(`${CONFIG.baseURL}/getAllScreensByTheater?theaterId=${theaterId}`, document.getElementById('screenSelect'), 'screenId', 'screenTitle');
});

function clearForm() {
    document.getElementById("showForm").reset();
}

function closeForm() {
    const modal = window.parent.document.getElementById("addShowModal");
    const iframe = window.parent.document.getElementById("addShowIframe");

    document.getElementById('showForm').reset();
    if (iframe) iframe.src = "";
    if (modal) modal.style.display = "none";
}

document.getElementById('showForm').addEventListener('submit', (e) => {
    e.preventDefault();

    const movieId = parseInt(document.getElementById('movieSelect').value);
    const screenId = parseInt(document.getElementById('screenSelect').value);
    const showDate = document.getElementById('showDate').value;
    const showTime = document.getElementById('showTime').value + ':00';

    const showPriceCategoryDTOS = Array.from(document.querySelectorAll('#seatPrices input')).map(input => ({
        seatCategoryId: parseInt(input.getAttribute('data-seat-id')),
        price: parseFloat(input.value)
    }));

    const payload = {
        movieId,
        screenId,
        showDate,
        showTime,
        showPriceCategoryDTOS
    };

    document.getElementById("showForm").addEventListener("submit", function (e) {
        e.preventDefault();
        $.ajax({
            url: `${CONFIG.baseURL}/addShow`,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(payload),
            xhrFields: {
                withCredentials: true
            },
            success: function () {
                closeForm();
                const showCreatedEvent = new CustomEvent('show-added', {
                    detail: {message: 'Show created successfully!'}
                });
                window.parent.dispatchEvent(showCreatedEvent);
            },
            error: function (xhr, status, error) {
                console.error("Error adding show:", error);
                let response = JSON.parse(xhr.responseText);
                showMessage(response.message);
            }
        });
    });
});