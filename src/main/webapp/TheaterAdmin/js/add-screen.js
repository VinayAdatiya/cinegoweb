import {loadSeatPalette} from './seat-pallete.js';
import {loadScreenTypes} from './screen-info.js';
import {loadTheaterInfo} from './theater-info.js';
import {setupSeatGridInput} from './seat-layout.js';

function setupScreenFormSubmission() {
    const screenForm = document.querySelector('#screenForm');

    screenForm.addEventListener('submit', async function (e) {
        e.preventDefault();

        const seats = Array.from(document.querySelectorAll('#seatLayout .seat')).map(function (seatElement) {
            const type = parseInt(seatElement.dataset.type);
            if (type > 0) {
                return {
                    rowNum: parseInt(seatElement.dataset.row),
                    colNum: parseInt(seatElement.dataset.col),
                    seatCategory: { seatCategoryId: type }
                };
            }
        }).filter(seat => seat !== undefined);

        const payload = {
            screenTitle: document.querySelector('#screenTitle').value,
            totalSeats: parseInt(document.querySelector('#totalSeats').value),
            screenType: { screenTypeId: parseInt(document.querySelector('#screenType').value) },
            theater: { theaterId: parseInt(document.querySelector('#theaterId').value) },
            layout: JSON.stringify({
                rowNum: parseInt(document.querySelector('#rowNum').value),
                colNum: parseInt(document.querySelector('#colNum').value),
                seats: seats
            })
        };

        try {
            await addScreen(payload);
            closeForm();
            window.parent.dispatchEvent(new CustomEvent('screen-added', {
                detail: { message: 'Screen created successfully!' }
            }));
        } catch {
            alert('Failed to create screen.');
        }
    });
}

async function addScreen(payload) {
    try {
        const response = await $.ajax({
            url: `${CONFIG.baseURL}/addScreen`,
            type: 'POST',
            contentType: 'application/json',
            xhrFields: { withCredentials: true },
            data: JSON.stringify(payload)
        });
        return response;
    } catch (error) {
        console.error('Error adding screen:', error);
        throw error;
    }
}

function closeForm() {
    const modal = window.parent.document.getElementById("addScreenModal");
    const iframe = window.parent.document.getElementById("addScreenIframe");

    document.getElementById('screenForm').reset();
    if (iframe) iframe.src = "";
    if (modal) modal.style.display = "none";
}

async function prefillTheaterInfo() {
    const theater = await loadTheaterInfo();
    if (theater) {
        const nameInput = document.querySelector('#theaterName');
        const idInput = document.querySelector('#theaterId');

        if (nameInput && idInput) {
            nameInput.value = theater.theaterName;
            idInput.value = theater.theaterId;
        } else {
            console.warn('Theater input elements not found.');
        }
    } else {
        alert('Failed to load theater information.');
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    loadSeatPalette();
    loadScreenTypes();
    await prefillTheaterInfo();
    setupSeatGridInput();
    setupScreenFormSubmission();
});