window.onload = function () {
    fetchAllMovies();
};

document.getElementById("addMovieBtn").addEventListener("click", function () {
    document.getElementById("movieFormFrame").src = "add-movie.html";
    document.getElementById("movieModal").style.display = "block";
});

function closeModal() {
    document.getElementById("movieModal").style.display = "none";
    document.getElementById("movieFormFrame").src = "";
}

window.addEventListener('message', function (event) {
    if (event.data.type === 'movieAdded') {
        closeModal();
        showPopupMessage("Movie Added Successfully!")
        fetchAllMovies();
    }
});

function fetchAllMovies() {
    $.ajax({
        url: `${CONFIG.baseURL}/getAllMovies`,
        type: 'GET',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result && result.data) {
                renderTable(result.data);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error fetching movies:', error);
        }
    });
}

function renderTable(movies) {
    const tbody = document.getElementById("moviesBody");
    tbody.innerHTML = "";

    movies.forEach((movie, index) => {
        const [year, month, day] = movie.movieReleaseDate;
        const releaseDate = `${String(day).padStart(2, '0')}-${String(month).padStart(2, '0')}-${year}`;
        console.log(releaseDate);

        const row = `
            <tr>
                <td>${index + 1}</td>
                <td align="center">${movie.movieTitle}</td>
                <td align="center">${movie.movieRating}</td>
                <td align="center">${releaseDate}</td>
                <td align="center">
                    <button class="view-btn" onclick='viewMovie(${JSON.stringify(JSON.stringify(movie))})'>View</button>
                    <button class="update-btn" onclick='updateMovie(${movie.movieId})'>Update</button>
                    <button class="delete-btn" onclick='deleteMovie(${movie.movieId})'>Delete</button>
                </td>
            </tr>
        `;

        tbody.insertAdjacentHTML('beforeend', row);
    });
}

function viewMovie(movieStr) {
    const movie = JSON.parse(movieStr);
    const container = document.getElementById("movieDetailsContent");

    const languages = movie.languages.map(l => l.languageName).join(', ');
    const genres = movie.genres.map(g => g.genreName).join(', ');
    const formats = movie.formats.map(f => f.formatName).join(', ');
    const crew = movie.movieCrewEntries.map(entry =>
        `${entry.crew.crewName} (${entry.crewDesignation.designationName}) as "${entry.characterName}"`
    ).join('<br>');

    container.innerHTML = `
        <p><strong>Title:</strong> ${movie.movieTitle}</p>
        <p><strong>Rating:</strong> ${movie.movieRating}</p>
        <p><strong>Duration:</strong> ${movie.movieDuration[0]}h ${movie.movieDuration[1]}m</p>
        <p><strong>Description:</strong> ${movie.movieDescription}</p>
        <p><strong>Languages:</strong> ${languages}</p>
        <p><strong>Genres:</strong> ${genres}</p>
        <p><strong>Formats:</strong> ${formats}</p>
        <p><strong>Crew:</strong><br>${crew}</p>
        <img src="${movie.moviePosterPath}" alt="Poster" style="max-width:200px; margin-top:10px;">
    `;

    document.getElementById("movieDetailModal").style.display = "block";
}

function closeDetails() {
    document.getElementById("movieDetailModal").style.display = "none";
}

function updateMovie(movieId) {
    fetchMovieData(movieId);
}

function deleteMovie(movieId) {
    if (confirm("Are you sure you want to delete this movie?")) {
        $.ajax({
            url: `${CONFIG.baseURL}/deleteMovie?movieId=${encodeURIComponent(movieId)}`,
            type: 'DELETE',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                console.log("Delete result:", result);
                showPopupMessage("Movie deleted successfully.");
                fetchAllMovies();
            },
            error: function (xhr, status, error) {
                console.error('Error deleting movie:', error);
                showPopupMessage("Error deleting movie");
            }
        });
    }
}

function fetchMovieData(movieId) {
    fetch(`${CONFIG.baseURL}/fetchMovie?movieId=${movieId}`, {
        credentials: 'include'
    })
        .then(response => response.json())
        .then(data => {
            sendMovieDataToIframe(data);
        })
        .catch(error => {
            console.error('Error fetching movie data:', error);
        });
}

function sendMovieDataToIframe(movie) {
    const iframe = document.getElementById('movieFormFrame');
    if (iframe) {
        iframe.contentWindow.postMessage({
            type: 'editMovie',
            movie: movie
        }, '*');
    } else {
        console.error('Iframe not found');
    }
}