import { getCurrentCategory, getSeatColors } from './seat-pallete.js';

let isDragging = false;

export function setupSeatGridInput() {
    $('#rowNum, #colNum').on('input', function () {
        const rows = parseInt($('#rowNum').val());
        const cols = parseInt($('#colNum').val());
        if (!isNaN(rows) && !isNaN(cols) && rows > 0 && cols > 0) {
            $('#totalSeats').val(rows * cols);
            generateSeats(rows, cols);
        } else {
            $('#totalSeats').val(0);
        }
    });
}

export function generateSeats(rows, cols) {
    const container = $('#seatLayout').empty().css({
        gridTemplateColumns: `repeat(${cols}, 44px)`
    });

    for (let r = 1; r <= rows; r++) {
        for (let c = 1; c <= cols; c++) {
            const seat = $(`<div class="seat" data-row="${r}" data-col="${c}" data-type="0"></div>`);
            seat.css({
                backgroundColor: '#e0e0e0',
                border: '1px solid #aaa',
                width: '40px',
                height: '40px',
                cursor: 'pointer'
            });

            seat.on('mousedown', () => {
                isDragging = true;
                setSeatType(seat);
            });
            seat.on('mouseenter', () => {
                if (isDragging) setSeatType(seat);
            });
            seat.on('mouseup', () => isDragging = false);

            container.append(seat);
        }
    }

    $(document).on('mouseup', () => isDragging = false);
}

function setSeatType($seat) {
    const type = getCurrentCategory();
    const colors = getSeatColors();
    if (!type) return;

    $seat.attr('data-type', type);
    $seat.css('background-color', colors[type] || '#ccc');
}