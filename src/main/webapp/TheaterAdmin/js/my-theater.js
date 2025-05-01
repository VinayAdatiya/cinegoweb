import {loadTheaterInfo} from './theater-info.js';
import {fetchCitiesWithSelected} from "../../cityUtils.js";

document.addEventListener('DOMContentLoaded', () => {
    loadTheaterInfo().then(data => {
        const setValue = (selector, value) => {
            const element = document.querySelector(selector);
            if (element) element.value = value || '';
        };

        if (!data) {
            setValue('#theaterName', 'No theater found');
            return;
        }

        setValue('#theaterId', data.theaterId);
        setValue('#theaterName', data.theaterName);
        setValue('#theaterRating', data.theaterRating);

        const address = data.theaterAddress || {};
        setValue('#addressId', address.addressId);
        setValue('#addressLine1', address.addressLine1);
        setValue('#addressLine2', address.addressLine2);
        setValue('#pincode', address.pincode);

        const admin = data.theaterAdmin || {};
        setValue('#adminEmail', admin.email);

        const cityId = address?.city?.cityId;
        fetchCitiesWithSelected(cityId);
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('#theater-form');

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        handleSubmit();
    });

    const handleSubmit = async () => {
        const getValue = (id) => document.querySelector(id)?.value || '';
        const toInt = (val) => parseInt(val, 10) || 0;

        const payload = {
            theaterAdmin: {
                email: getValue('#adminEmail'),
                password: getValue('#adminPassword')
            },
            theaterId: toInt(getValue('#theaterId')),
            theaterName: getValue('#theaterName'),
            theaterRating: toInt(getValue('#theaterRating')),
            theaterAddress: {
                addressId: toInt(getValue('#addressId')),
                addressLine1: getValue('#addressLine1'),
                addressLine2: getValue('#addressLine2'),
                pincode: toInt(getValue('#pincode')),
                city: {
                    cityId: toInt(getValue('#city'))
                }
            }
        };

        console.log('Submitting payload:', payload);

        try {
            const response = await fetch(`${CONFIG.baseURL}/updateTheater`, {
                method: 'PUT',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            const result = await response.json();

            if (!response.ok) {
                showMessage(result.message || `Error ${response.status}: ${response.statusText}`);
                return;
            }

            showPopupMessage(result.message || 'Theater updated successfully!');
        } catch (error) {
            console.error("Error updating theater:", error);
            showMessage('An unexpected error occurred while updating the theater.');
        }
    };
});

