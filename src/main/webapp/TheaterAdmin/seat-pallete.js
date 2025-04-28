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
    $.get(`${CONFIG.baseURL}/getAllSeatCategories`, function (response) {
        const seatCategories = response.data;
        const palette = $('#seatPalette').empty().css({
            display: 'flex',
            flexWrap: 'wrap',
            justifyContent: 'flex-start',
        });

        seatCategories.forEach((cat, index) => {
            const color = colours[index % colours.length];
            const btn = $(`<button type="button" class="btn seat-type" data-type="${cat.seatCategoryId}" style="background-color:${color}; margin: 4px;">${cat.seatType}</button>`);
            palette.append(btn);
            seatColors[cat.seatCategoryId] = color;
        });

        $('.seat-type').click(function () {
            $('.seat-type').removeClass('selected');
            $(this).addClass('selected');
            currentCategory = $(this).data('type');
        });

        $('.seat-type').first().click();
    });
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