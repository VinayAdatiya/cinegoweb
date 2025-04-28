import {fetchScreenById, fetchScreensByTheaterId} from './screen-info.js';
import {getSeatColors, renderSeatLegend} from './seat-pallete.js';

// $(document).ready(() => {
//     renderSeatLegend();
// });
document.addEventListener('DOMContentLoaded', () => {
    renderSeatLegend();
});

// function clearTable(tableSelector) {
//     $(`${tableSelector} tbody`).empty();
// }
function clearTable(tableSelector) {
    const table = document.querySelector(`${tableSelector} tbody`);
    if (table) {
        table.innerHTML = '';
    }
}

function populateScreensTable(tableSelector, screens) {
    const $tbody = $(`${tableSelector} tbody`);
    $tbody.empty();

    if (!screens || !screens.length) {
        $tbody.append(`<tr><td colspan="4">No screens found</td></tr>`);
        return;
    }

    screens.forEach((screen, index) => {
        const row = `
            <tr>
                <td>${index + 1}</td>
                <td>${screen.screenTitle}</td>
                <td>
                   <button class="action-btn view-btn" data-screen-id="${screen.screenId}">View</button>
                    <button class="action-btn update-btn" data-screen-id="${screen.screenId}">Update</button>
                   <button class="action-btn delete-btn" data-id="${screen.screenId}">Delete</button>
                </td>
            </tr>
        `;
        $tbody.append(row);
    });
    bindActionButtons();
}

async function loadAndDisplayScreens() {
    clearTable('#screensTable');
    const screens = await fetchScreensByTheaterId(localStorage.getItem("theaterId"));

    if (screens === null) {
        $('#screensTable tbody').append('<tr><td colspan="3">Error loading screens</td></tr>');
        return;
    }

    populateScreensTable('#screensTable', screens);
}

$(document).ready(function () {
    loadAndDisplayScreens();
});

$('#loadScreensBtn').on('click', function () {
    loadAndDisplayScreens();
});

$('#add-btn').on('click', async function () {
    const theaterId = $('#theaterId').val();
    const screenData = getScreenData();

    const result = await addScreen(theaterId, screenData);

    if (result.success) {
        loadAndDisplayScreens();
    } else {
        alert('Error adding screen');
    }
});

function bindActionButtons() {
    $('#screensTable').on('click', '.view-btn', function () {
        const screenId = $(this).data('screen-id');
        viewScreen(screenId);
    });

    $('#screensTable').on('click', '.update-btn', function () {
        const screenId = $(this).data('screen-id');
        editScreen(screenId);
    });

    $('#screensTable').on('click', '.delete-btn', function () {
        const screenId = $(this).data('id');
        deleteScreen(screenId);
    });
}

async function viewScreen(screenId) {
    console.log(screenId);
    const screen = await fetchScreenById(screenId);
    console.log(screen);
    populateScreenForm(screen, false);
}

async function editScreen(screenId) {
    const screen = await fetchScreenById(screenId);
    populateScreenForm(screen, true);
}

document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("addScreenModal");
    const iframe = document.getElementById("addScreenIframe");

    document.getElementById("openFormBtn").addEventListener("click", () => {
        iframe.src = "add-screen.html";
        modal.style.display = "block";
    });

    document.querySelector(".close").addEventListener("click", () => {
        iframe.src = "";
        modal.style.display = "none";
    });
});

function deleteScreen(screenId) {
    if (confirm("Are you sure you want to delete this screen?")) {
        $.ajax({
            url: `${CONFIG.baseURL}/deleteScreen?screenId=${encodeURIComponent(screenId)}`,
            type: 'DELETE',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                console.log("Delete result:", result);
                showPopupMessage("Screen deleted successfully.");
                loadAndDisplayScreens();
            },
            error: function (xhr, status, error) {
                console.error('Error deleting screen:', error);
            }
        });
    }
}

function populateScreenForm(screenData, isUpdateMode = false) {
    $('#screenId').val(screenData.screenId);
    $('#screenTitle').val(screenData.screenTitle);
    $('#totalSeats').val(screenData.totalSeats);
    $('#screenType').val(screenData.screenType.screenType);
    $('#theaterName').val(screenData.theaterName);

    const layout = JSON.parse(screenData.layout);
    renderSeatLayout(JSON.parse(screenData.layout));

    $('#screenForm input').each(function () {
        $(this).prop('disabled', !isUpdateMode);
    });

    $('#screenType, #theaterName').prop('disabled', true);
    $('#submitButton').toggle(isUpdateMode);
    $('#screenFormModal').show();
}

function renderSeatLayout(layout) {
    const {rowNum, colNum, seats} = layout;
    const seatMap = {};
    const seatColors = getSeatColors();

    seats.forEach(seat => {
        seatMap[`${seat.rowNum}-${seat.colNum}`] = seat.seatCategory.seatCategoryId;
    });

    let html = '<table style="border-collapse: collapse;">';

    for (let r = 1; r <= rowNum; r++) {
        html += '<tr>';
        for (let c = 1; c <= colNum; c++) {
            const key = `${r}-${c}`;
            const categoryId = seatMap[key] || null;
            let color = '#e0e0e0'; // default empty/undefined seat

            if (categoryId && seatColors[categoryId]) {
                color = seatColors[categoryId];
            }

            html += `<td style="width: 30px; height: 30px; border: 1px solid #ccc; background: ${color}; text-align: center;"></td>`;
        }
        html += '</tr>';
    }

    html += '</table>';
    $('#layoutPreview').html(html);
}

document.getElementById("closeScreenBtn").addEventListener("click", () => {
    const formModal = document.getElementById("screenFormModal");
    const form = document.getElementById("screenForm");
    form.reset();
    formModal.style.display = "none";
});