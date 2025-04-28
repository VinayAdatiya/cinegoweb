import {loadTheaterInfo} from './theater-info.js';
import {fetchCitiesWithSelected} from "../cityUtils.js";


$(document).ready(() => {
    loadTheaterInfo().then(data => {
        if (!data) {
            $('#theaterName').val('No theater found');
            return;
        }

        $('#theaterId').val(data.theaterId || '');
        $('#theaterName').val(data.theaterName || '');
        $('#theaterRating').val(data.theaterRating || '');

        const address = data.theaterAddress || {};
        $('#addressId').val(address.addressId || '');
        $('#addressLine1').val(address.addressLine1 || '');
        $('#addressLine2').val(address.addressLine2 || '');
        $('#pincode').val(address.pincode || '');

        const admin = data.theaterAdmin || {};
        $('#adminEmail').val(admin.email || '');

        const cityId = address?.city?.cityId;
        fetchCitiesWithSelected(cityId);
    });
});

$('#theater-form').on('submit', function (e) {
    e.preventDefault();

    const payload = {
        theaterAdmin: {
            email: $('#adminEmail').val(),
            password: $('#adminPassword').val()
        },
        theaterId: parseInt($('#theaterId').val(), 10),
        theaterName: $('#theaterName').val(),
        theaterRating: parseInt($('#theaterRating').val(), 10),
        theaterAddress: {
            addressId: parseInt($('#addressId').val(), 10),
            addressLine1: $('#addressLine1').val(),
            addressLine2: $('#addressLine2').val(),
            pincode: parseInt($('#pincode').val(), 10),
            city: {
                cityId: parseInt($('#city').val(), 10)
            }
        }
    };

    console.log('Submitting payload:', payload);

    $.ajax({
        url: `${CONFIG.baseURL}/updateTheater`,
        method: 'PUT',
        contentType: 'application/json',
        xhrFields: {
            withCredentials: true
        },
        data: JSON.stringify(payload),
        success: function (response) {
            showPopupMessage('Theater updated successfully!');
        },
        error: function (xhr, status, error) {
            console.error("Error updating theater:", error);
            let response = JSON.parse(xhr.responseText);
            showMessage(response.message);
        }
    });
});
