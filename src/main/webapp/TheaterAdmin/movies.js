window.onload = function () {
    fetchAllMovies();
};

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
    let rows = "";

    movies.forEach((movie, index) => {
        const [year, month, day] = movie.movieReleaseDate;
        const releaseDate = `${String(day).padStart(2, '0')}-${String(month).padStart(2, '0')}-${year}`;
        console.log(releaseDate);
        rows += `
            <tr>
                <td>${index + 1}</td>
                <td align="center">${movie.movieTitle}</td>
                <td align="center">${movie.movieRating}</td>
                <td align="center">${releaseDate}</td>
                <td align="center">
                    <button class="view-btn" onclick='viewMovieDetails(${movie.movieId})'>View</button>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = rows;
}

async function fetchMovieData(movieId) {
    try {
        const response = await fetch(`${CONFIG.baseURL}/fetchMovie?movieId=${movieId}`, {
            credentials: 'include'
        });
        const data = await response.json();
        return data.data;
    } catch (error) {
        console.error('Error fetching movie data:', error);
    }
}

async function viewMovieDetails(movieId) {

    const movie = await fetchMovieData(movieId);
    console.log(movie);
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