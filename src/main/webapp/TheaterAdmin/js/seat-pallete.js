let currentCategory = null;
let seatColors = {};
const colours = ['#FF7F00', '#DAA520', '#228B22', '#4682B4', '#6A5ACD', '#D32F2F', '#8F00FF'];

export function getCurrentCategory() {
    return currentCategory;
}

export function getSeatColors() {
    return seatColors;
}

export function loadSeatPalette() {
    fetch(`${CONFIG.baseURL}/getAllSeatCategories`)
        .then(response => response.json())
        .then(data => {
            const seatCategories = data.data;
            const palette = document.querySelector('#seatPalette');
            palette.innerHTML = '';
            Object.assign(palette.style, {
                display: 'flex',
                flexWrap: 'wrap',
                justifyContent: 'flex-start',
            });

            seatCategories.forEach((cat, index) => {
                const color = colours[index % colours.length];
                const btn = document.createElement('button');
                btn.type = 'button';
                btn.classList.add('btn', 'seat-type');
                btn.dataset.type = cat.seatCategoryId;
                btn.style.backgroundColor = color;
                btn.style.margin = '4px';
                btn.textContent = cat.seatType;

                palette.appendChild(btn);
                seatColors[cat.seatCategoryId] = color;
            });

            const seatButtons = document.querySelectorAll('.seat-type');
            seatButtons.forEach(btn => {
                btn.addEventListener('click', function () {
                    seatButtons.forEach(button => button.classList.remove('selected'));
                    this.classList.add('selected');
                    currentCategory = this.dataset.type;
                });
            });

            if (seatButtons.length > 0) {
                seatButtons[0].click();
            }
        })
        .catch(error => console.error('Error loading seat categories:', error));
}


export function renderSeatLegend(containerId = '#seatLegend') {
    const container = $(containerId).empty().css({
        display: 'flex',
        flexWrap: 'wrap',
        gap: '10px',
        margin: '10px 0'
    });

    $.get(`${CONFIG.baseURL}/getAllSeatCategories`, function (response) {
        const seatCategories = response.data;
        seatCategories.forEach((cat, index) => {
            const color = colours[index % colours.length];
            seatColors[cat.seatCategoryId] = color;

            const legendItem = $(`
                <div style="display:flex; align-items:center; gap:6px;">
                    <div style="width:20px; height:20px; background-color:${color}; border:1px solid #333;"></div>
                    <span style="font-size:14px;">${cat.seatType}</span>
                </div>
            `);

            container.append(legendItem);
        });
    });
}