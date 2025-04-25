export async function loadTheaterInfo() {
    try {
        const response = await fetch(`${CONFIG.baseURL}/getTheaterByAdminId`, {
            credentials: 'include'
        });
        const data = await response.json();
        return data.data;
    } catch (error) {
        console.error("Error loading theater info:", error);
        return null;
    }
}