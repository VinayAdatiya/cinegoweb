function getQueryParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

const movieId = getQueryParameter('movieId');
if (movieId) {
    fetchTheatersAndShows(movieId);
} else {
    console.error('No movieId found in the query string');
}

async function fetchTheatersAndShows(movieId) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/getShowByMovie?movieId=${movieId}`, {
            credentials: 'include'
        });

        const data = await response.json();
        const theaterShowMap = mapShowsByTheaterId(data);
        const tableBody = renderTable(theaterShowMap);

        const table = document.createElement('table');
        table.appendChild(tableBody);
        document.body.appendChild(table);
    } catch (error) {
        console.error('Error fetching theaters and shows data:', error);
    }
}

function mapShowsByTheaterId(response) {
    const theaterShowMap = new Map();

    if (!response || !Array.isArray(response.data)) return theaterShowMap;

    response.data.forEach(show => {
        const theaterId = show.screen?.theaterId;
        if (theaterId != null) {
            if (!theaterShowMap.has(theaterId)) {
                theaterShowMap.set(theaterId, []);
            }
            theaterShowMap.get(theaterId).push(show);
        }
    });

    return theaterShowMap;
}

function renderTable(theaterShowMap) {
    const tableBody = document.createElement('tbody');
    tableBody.innerHTML = '';
    for (const [_, shows] of theaterShowMap.entries()) {
        if (shows.length === 0) continue;

        const theaterName = shows[0].screen?.theaterName || "Unknown Theater";
        const tr = document.createElement('tr');

        const tdTheater = document.createElement('td');
        tdTheater.textContent = theaterName;
        tr.appendChild(tdTheater);

        const tdShows = document.createElement('td');

        shows.forEach(show => {
            const [year, month, day] = show.showDate;
            const [hour, minute] = show.showTime;
            const formattedDate = `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
            const formattedTime = `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;

            const showInfoDiv = document.createElement('div');
            showInfoDiv.style.marginBottom = "10px";
            showInfoDiv.style.padding = "8px 46px";
            showInfoDiv.style.borderRadius = "4px";
            showInfoDiv.style.backgroundColor = "#f9f9f9";
            showInfoDiv.style.boxShadow = "0 1px 4px rgba(0, 0, 0, 0.1)";

            const button = document.createElement('button');
            button.textContent = formattedTime;
            button.style.padding = "6px 12px";
            button.style.backgroundColor = "#3498db";
            button.style.color = "white";
            button.style.border = "none";
            button.style.borderRadius = "4px";
            button.style.cursor = "pointer";

            button.addEventListener('click', function () {
                const showId = show.showId;
                window.location.href = './book-show.html?showId=' + showId;
            });

            const dateSpan = document.createElement('span');
            dateSpan.textContent = formattedDate + ' ';
            dateSpan.style.marginRight = "8px";
            dateSpan.style.fontWeight = "500";

            showInfoDiv.appendChild(dateSpan);
            showInfoDiv.appendChild(button);

            tdShows.appendChild(showInfoDiv);
        });

        tr.appendChild(tdShows);
        tableBody.appendChild(tr);
    }

    return tableBody;
}