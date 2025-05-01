import { getCurrentCategory, getSeatColors } from './seat-pallete.js';

let isDragging = false;

export function setupSeatGridInput() {
    const rowNumInput = document.querySelector('#rowNum');
    const colNumInput = document.querySelector('#colNum');
    const totalSeatsInput = document.querySelector('#totalSeats');

    rowNumInput.addEventListener('input', function () {
        const rows = parseInt(rowNumInput.value);
        const cols = parseInt(colNumInput.value);

        if (!isNaN(rows) && !isNaN(cols) && rows > 0 && cols > 0) {
            totalSeatsInput.value = rows * cols;
            generateSeats(rows, cols);
        } else {
            totalSeatsInput.value = 0;
        }
    });

    colNumInput.addEventListener('input', function () {
        const rows = parseInt(rowNumInput.value);
        const cols = parseInt(colNumInput.value);

        if (!isNaN(rows) && !isNaN(cols) && rows > 0 && cols > 0) {
            totalSeatsInput.value = rows * cols;
            generateSeats(rows, cols);
        } else {
            totalSeatsInput.value = 0;
        }
    });
}

export function generateSeats(rows, cols) {
    const container = document.querySelector('#seatLayout');
    container.innerHTML = '';
    container.style.gridTemplateColumns = `repeat(${cols}, 44px)`;

    for (let r = 1; r <= rows; r++) {
        for (let c = 1; c <= cols; c++) {
            const seat = document.createElement('div');
            seat.className = 'seat';
            seat.dataset.row = r;
            seat.dataset.col = c;
            seat.dataset.type = '0';

            Object.assign(seat.style, {
                backgroundColor: '#e0e0e0',
                border: '1px solid #aaa',
                width: '40px',
                height: '40px',
                cursor: 'pointer'
            });

            seat.addEventListener('mousedown', () => {
                isDragging = true;
                setSeatType(seat); // Now passing a native DOM element
            });

            seat.addEventListener('mouseenter', () => {
                if (isDragging) setSeatType(seat);
            });

            seat.addEventListener('mouseup', () => {
                isDragging = false;
            });

            container.appendChild(seat);
        }
    }

    document.addEventListener('mouseup', () => {
        isDragging = false;
    });
}

function setSeatType(seatElement) {
    const type = getCurrentCategory();
    const colors = getSeatColors();
    if (!type) return;

    seatElement.setAttribute('data-type', type);
    seatElement.style.backgroundColor = colors[type] || '#ccc';
}
