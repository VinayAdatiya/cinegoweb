let selectedSeats = null;

function getQueryParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

const showId = getQueryParameter('showId');
if (showId) {
    fetchShow(showId);
    loadPaymentMethods();
} else {
    console.error('No movieId found in the query string');
}

async function fetchShow(showId) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/getShow?showId=${encodeURIComponent(showId)}`, {
            credentials: 'include'
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const result = await response.json();
        if (result?.data) {
            const categoryMap = await displaySeatCategoriesWithPrice(result.data.showPriceCategoryList);
            renderSeats(result.data, categoryMap);

        } else {
            console.warn('No show data returned:', result);
        }
    } catch (error) {
        console.error('Error fetching show:', error);
    }
}

function renderSeats(data, categoryMap) {
    if (!data || !data.screen || !data.screen.layout) {
        console.warn('Invalid seat data provided:', data);
        return;
    }
    selectedSeats = new Set();
    const layout = JSON.parse(data.screen.layout);
    const seatLayoutDiv = document.getElementById("seatLayout");
    seatLayoutDiv.innerHTML = '';

    let isDragging = false;

    function handleMouseDown(e) {
        if (e.target.classList.contains('available')) {
            isDragging = true;
            toggleSeatSelection(e.target);
        }
    }

    function handleMouseOver(e) {
        if (isDragging && e.target.classList.contains('available')) {
            toggleSeatSelection(e.target);
        }
    }

    function handleMouseUp() {
        isDragging = false;
    }

    function toggleSeatSelection(seatDiv) {
        const seatKey = seatDiv.dataset.key;
        const seatId = seatDiv.dataset.seatId;

        if (selectedSeats.has(seatKey)) {
            seatDiv.classList.remove('selected');
            selectedSeats.delete(seatKey);
        } else {
            seatDiv.classList.add('selected');
            selectedSeats.add(seatKey);
            console.log(`Selected seatId: ${seatId}`);
        }
    }

    document.removeEventListener('mouseup', handleMouseUp);
    document.addEventListener('mouseup', handleMouseUp);

    for (let row = 1; row <= layout.rowNum; row++) {
        const seatRowDiv = document.createElement("div");
        seatRowDiv.classList.add("seat-row");

        for (let col = 1; col <= layout.colNum; col++) {
            const seat = data.showSeatList.find(seat => seat.rowNum === row && seat.colNum === col);
            const seatDiv = document.createElement("div");
            seatDiv.classList.add("seat");
            seatDiv.dataset.key = `${row}-${col}`;

            if (seat) {
                seatDiv.dataset.seatId = seat.seatId;
                seatDiv.dataset.categoryId = seat.seatCategoryId;

                const seatType = categoryMap.get(seat.seatCategoryId) || "Unknown";
                seatDiv.innerHTML = `<div><strong>${seatType}</strong></div><div>${row}-${col}</div>`;

                if (!seat.booked) {
                    seatDiv.classList.add("available");
                    seatDiv.addEventListener('mousedown', handleMouseDown);
                    seatDiv.addEventListener('mouseover', handleMouseOver);
                } else {
                    seatDiv.classList.add("booked");
                    seatDiv.style.cursor = "not-allowed";
                }
            } else {
                seatDiv.classList.add("empty");
                seatDiv.innerHTML = `${row}-${col}`;
            }

            seatRowDiv.appendChild(seatDiv);
        }

        seatLayoutDiv.appendChild(seatRowDiv);
    }

}

async function displaySeatCategoriesWithPrice(showPriceCategoryList) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/getAllSeatCategories`);
        const categoryResult = await response.json();

        if (!categoryResult?.data) {
            console.error("Failed to load seat categories");
            return new Map();
        }

        const seatCategoriesMap = new Map();
        categoryResult.data.forEach(cat => {
            seatCategoriesMap.set(cat.seatCategoryId, cat.seatType);
        });

        const mergedList = showPriceCategoryList.map(item => {
            const name = seatCategoriesMap.get(item.seatCategoryId) || "Unknown";
            return {
                seatCategoryId: item.seatCategoryId,
                seatType: name,
                price: typeof item.price === 'number' ? item.price : 0
            };
        });

        const container = document.getElementById("seatCategories");
        container.innerHTML = "<h3>Seat Categories & Prices</h3>";
        const ul = document.createElement("ul");
        ul.style.listStyle = "none";
        ul.style.padding = "0";

        mergedList.forEach(item => {
            const li = document.createElement("li");
            li.style.marginBottom = "6px";
            li.innerHTML = `<strong>${item.seatType}</strong> - ₹${item.price}`;
            ul.appendChild(li);
        });

        container.appendChild(ul);

        return seatCategoriesMap;

    } catch (error) {
        console.error("Error fetching seat categories:", error);
        return new Map();
    }
}

async function loadPaymentMethods() {
    try {
        const response = await fetch(`${CONFIG.baseURL}/getAllPaymentMethod`);
        const result = await response.json();

        if (!result?.data || !Array.isArray(result.data)) {
            console.error("Invalid payment methods response");
            return;
        }

        const select = document.getElementById("paymentMethodSelect");
        select.innerHTML = '<option value="">-- Select Payment Method --</option>';

        result.data.forEach(method => {
            const option = document.createElement("option");
            option.value = method.paymentMethodId;
            option.textContent = method.paymentMethod;
            select.appendChild(option);
        });

    } catch (error) {
        console.error("Error fetching payment methods:", error);
    }
}

document.getElementById("submitBooking").addEventListener("click", async () => {
    const selectedPaymentMethod = document.getElementById("paymentMethodSelect").value;

    if (!selectedPaymentMethod) {
        alert("Please select a payment method.");
        return;
    }

    if (selectedSeats.size === 0) {
        alert("Please select at least one seat.");
        return;
    }

    const showSeatList = Array.from(selectedSeats).map(seatKey => {
        const seatDiv = document.querySelector(`[data-key='${seatKey}']`);
        return {
            seatId: parseInt(seatDiv.dataset.seatId)
        };
    });

    const payload = {
        showId: parseInt(showId),
        numberOfSeats: showSeatList.length,
        paymentMethodId: parseInt(selectedPaymentMethod),
        showSeatList
    };

    console.log("Booking Payload:", payload);

    const booking = await createBooking(payload);

    if (booking == null || booking?.error) {
        alert("Booking Failed! Please retry!");
        window.location.href = './movies.html';
    } else {
        const bookingId = booking?.bookingId;
        const formatDate = (arr) => `${arr[2]}/${arr[1]}/${arr[0]}`;
        const formatTime = (arr) => `${arr[0].toString().padStart(2, '0')}:${arr[1].toString().padStart(2, '0')}`;
        const formatDuration = (arr) => `${arr[0]}h ${arr[1]}m ${arr[2]}s`;

        const seatInfo = booking.bookedShowSeats.map(
            s => `${s.seatType} (Row ${s.row_num}, Col ${s.col_num})`
        ).join(", ");
        const message = `
    <strong>🎉 Booking Confirmed! Enjoy Your Movie!</strong><br><br>
    📽️ <strong>Movie</strong>: ${booking.movieTitle}<br>
    🕒 <strong>Show Time</strong>: ${formatTime(booking.showTime)} on ${formatDate(booking.showDate)}<br>
    🎞️ <strong>Duration</strong>: ${formatDuration(booking.movieDuration)}<br>
    🏢 <strong>Theater</strong>: ${booking.theaterName}<br>
    🖥️ <strong>Screen</strong>: ${booking.screenTitle} (${booking.screenType})<br>
    👤 <strong>Booked By</strong>: ${booking.userName}<br>
    💺 <strong>Seats</strong>: ${booking.numberOfSeats} — ${seatInfo}<br>
    💳 <strong>Payment Method</strong>: ${booking.paymentMethod.paymentMethod}<br>
    💰 <strong>Total Paid</strong>: ₹${booking.grandTotal.toFixed(2)}<br>
    📄 <strong>Booking ID</strong>: ${booking.bookingId}
`;
        showModal(message, bookingId);
    }
});

async function createBooking(bookingDetails) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/createBooking`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(bookingDetails),
            credentials: "include"
        });
        if (response.status === 200 || response.status === 201) {
            const result = await response.json();
            console.log(result);
            return result.data;
        } else {
            console.error(`Booking failed. Status: ${response.status}`);
            return null;
        }
    } catch (error) {
        console.error("Booking error:", error);
        return {error: true, message: error.message};
    }
}

function showModal(message, bookingId) {
    const modal = document.getElementById("responseModal");
    const modalMessage = document.getElementById("modalMessage");
    const closeBtn = document.getElementById("closeModal");

    modalMessage.innerHTML = message;
    modal.style.display = "block";

    const confirmBtn = document.getElementById("confirmBookingBtn");
    const cancelBtn = document.getElementById("cancelBookingBtn");
    const newConfirmBtn = confirmBtn.cloneNode(true);
    const newCancelBtn = cancelBtn.cloneNode(true);

    confirmBtn.replaceWith(newConfirmBtn);
    cancelBtn.replaceWith(newCancelBtn);

    let timeoutId = setTimeout(() => {
        console.warn("Booking timeout: auto-cancel triggered.");
        modal.style.display = "none";
        cancelBooking(bookingId);
    }, 180000);//3minutes

    newConfirmBtn.onclick = () => {
        clearTimeout(timeoutId);
        confirmBooking(bookingId);
        modal.style.display = "none";
    };

    newCancelBtn.onclick = () => {
        clearTimeout(timeoutId);
        cancelBooking(bookingId);
        modal.style.display = "none";
    };

    closeBtn.onclick = () => {
        clearTimeout(timeoutId);
        modal.style.display = "none";
    };

    window.onclick = (event) => {
        if (event.target === modal) {
            clearTimeout(timeoutId);
            modal.style.display = "none";
        }
    };
}

async function confirmBooking(bookingId) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/confirmBooking?bookingId=${bookingId}`, {
            method: "POST",
            credentials: "include"
        });

        const result = await response.json();
        alert(`Booking confirmed! ${result.message || ''}`);
        document.getElementById("responseModal").style.display = "none";
        window.location.href = './my-bookings.html';
    } catch (error) {
        alert("Failed to confirm booking: " + error.message);
    }
}

async function cancelBooking(bookingId) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/cancelBooking?bookingId=${bookingId}`, {
            method: "POST",
            credentials: "include"
        });

        const result = await response.json();
        alert(`Booking cancelled. ${result.message || ''}`);
        document.getElementById("responseModal").style.display = "none";
        window.location.href = './movies.html';
    } catch (error) {
        alert("Failed to cancel booking: " + error.message);
    }
}