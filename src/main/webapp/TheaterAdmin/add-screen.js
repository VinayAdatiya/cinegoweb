import {loadSeatPalette} from './seat-pallete.js';
import {loadScreenTypes} from './screen-info.js';
import {loadTheaterInfo} from './theater-info.js';
import {setupSeatGridInput} from './seat-layout.js';

function setupScreenFormSubmission() {
    $('#screenForm').on('submit', function (e) {
        e.preventDefault();

        const seats = $('#seatLayout .seat').map(function () {
            const type = parseInt($(this).data('type'));
            if (type > 0) {
                return {
                    rowNum: parseInt($(this).data('row')),
                    colNum: parseInt($(this).data('col')),
                    seatCategory: {seatCategoryId: type}
                };
            }
        }).get();

        const payload = {
            screenTitle: $('#screenTitle').val(),
            totalSeats: parseInt($('#totalSeats').val()),
            screenType: {screenTypeId: parseInt($('#screenType').val())},
            theater: {theaterId: parseInt($('#theaterId').val())},
            layout: JSON.stringify({
                rowNum: parseInt($('#rowNum').val()),
                colNum: parseInt($('#colNum').val()),
                seats: seats
            })
        };

        $.ajax({
            url: `${CONFIG.baseURL}/addScreen`,
            type: 'POST',
            contentType: 'application/json',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify(payload),
            success: () => {
                closeForm();
                const screenCreatedEvent = new CustomEvent('screen-added', {
                    detail: { message: 'Screen created successfully!' }
                });
                window.parent.dispatchEvent(screenCreatedEvent);
            }
            ,
            error: () => alert('Failed to create screen.')
        });
    });
}

function closeForm() {
    const modal = window.parent.document.getElementById("addScreenModal");
    const iframe = window.parent.document.getElementById("addScreenIframe");

    document.getElementById('screenForm').reset();
    if (iframe) iframe.src = "";
    if (modal) modal.style.display = "none";
}


$(document).ready(function () {
    loadSeatPalette();
    loadScreenTypes();
    loadTheaterInfo();
    setupSeatGridInput();
    setupScreenFormSubmission();
});