export function loadScreenTypes() {
    $.get(`${CONFIG.baseURL}/getAllScreenTypes`, response => {
        response.data.forEach(type => {
            $('#screenType').append(`<option value="${type.screenTypeId}">${type.screenType}</option>`);
        });
    });
}

export async function fetchScreensByTheaterId(theaterId) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/getAllScreensByTheater?theaterId=${encodeURIComponent(theaterId)}`, {
            credentials: 'include'
        });
        const result = await response.json();
        console.log(result.data)
        return result.data || [];
    } catch (error) {
        console.error('Failed to fetch screens:', error);
        return null;
    }
}

export async function fetchScreenById(screenId) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/getScreenById?screenId=${encodeURIComponent(screenId)}`, {
            credentials: 'include'
        });
        const result = await response.json();
        console.log(result.data)
        return result.data || [];
    } catch (error) {
        console.error('Failed to fetch screens:', error);
        return null;
    }
}