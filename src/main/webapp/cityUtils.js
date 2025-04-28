export function fetchCitiesWithSelected(selectedCityId = null) {
    $.ajax({
        url: `${CONFIG.baseURL}/fetchCities`,
        type: 'GET',
        dataType: 'json',
        credentials: 'include',
        success: function (result) {
            let cityDropdown = $('#city');
            cityDropdown.empty();
            cityDropdown.append('<option value="">Select City</option>');

            if (result.data.length === 0) {
                cityDropdown.append('<option value="">No Cities Available</option>');
                return;
            }

            $.each(result.data, function (index, city) {
                const isSelected = city.cityId === selectedCityId ? 'selected' : '';
                cityDropdown.append(`<option value="${city.cityId}" ${isSelected}>${city.cityName}</option>`);
            });
        },
        error: function (xhr, status, error) {
            console.error('Error fetching cities:', error);
        }
    });
}