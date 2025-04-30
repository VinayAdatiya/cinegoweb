let isUpdate = false;
let existingMovieData = null;
let duration = "";
let castCount = 0;
let crewMembers = [];
let designations = [];

document.getElementById('rating').addEventListener('input', function () {
    document.getElementById('ratingValue').textContent = parseFloat(this.value).toFixed(1);
});

$(document).ready(() => {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('mode') === 'update') {
        isUpdate = true;
        fetchMovieById(urlParams.get('id'));
    }
    loadAndRenderData();

    document.getElementById('castContainer').addEventListener('click', function(e) {
        if (e.target && e.target.classList.contains('removeBtn')) {
            let castRow = e.target.closest('.cast-row');
            if (castRow) {
                castRow.remove();
            }
        }
    });

    document.getElementById('addCastBtn').addEventListener('click', createCastRow);


    $('#movieForm').on('submit', function (e) {
        e.preventDefault();
        if (!validateDuration()) return;

        const movieData = buildMovieData();
        const method = isUpdate ? 'PUT' : 'POST';
        const url = isUpdate ? `${CONFIG.baseURL}/updateMovie` : `${CONFIG.baseURL}/addMovie`;

        $.ajax({
            url,
            method,
            headers: {'Content-Type': 'application/json'},
            xhrFields: {withCredentials: true},
            data: JSON.stringify(movieData),
            success: function () {
                document.dispatchEvent(new Event('movieAddedSuccessfully'));
                clearForm();
            },
            error: function (xhr, status, error) {
                console.error("Error adding crew:", error);
                let response = JSON.parse(xhr.responseText);
                showMessage(response.message);
            }
        });
    });
});

function validateDuration() {
    const hr = parseInt($('#hours').val(), 10);
    const min = parseInt($('#minutes').val(), 10);
    const sec = parseInt($('#seconds').val(), 10);

    if (hr === 0 && min === 0 && sec === 0) {
        alert("Movie duration cannot be 0:00:00");
        return false;
    }
    return true;
}

async function loadAndRenderData() {
    try {
        const [languagesData, genresData, formatsData, crewMembersData, designationsData] = await Promise.all([
            fetch(`${CONFIG.baseURL}/getLanguages`).then(res => res.json()),
            fetch(`${CONFIG.baseURL}/getGenres`).then(res => res.json()),
            fetch(`${CONFIG.baseURL}/getFormats`).then(res => res.json()),
            fetch(`${CONFIG.baseURL}/getCrewMembers`).then(res => res.json()),
            fetch(`${CONFIG.baseURL}/getCrewDesignations`).then(res => res.json())
        ]);

        renderLanguageCheckboxes(languagesData.data);
        renderGenresCheckboxes(genresData.data);
        renderFormatsCheckboxes(formatsData.data);

        crewMembers = crewMembersData.data;
        designations = designationsData.data;

        if (isUpdate && existingMovieData) {
            populateFormWithMovieData(existingMovieData);
        }
    } catch (error) {
        console.error("Error fetching dropdown data", error);
    }
}

function renderLanguageCheckboxes(languages) {
    const container0 = document.getElementById('language-container');
    container0.innerHTML = '';

    languages.forEach(language => {
        const label = document.createElement('label');
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = language.languageId;
        checkbox.value = language.languageName;
        checkbox.name = 'language';

        label.appendChild(checkbox);
        label.appendChild(document.createTextNode(' ' + language.languageName));
        container0.appendChild(label);
    });
}

let selectedLanguages = [];
document.getElementById("movieForm").addEventListener("submit", function (e) {
    selectedLanguages = Array.from(document.querySelectorAll('input[name="language"]:checked'))
        .map(cb => ({languageId: parseInt(cb.id)}));

    console.log("Submitting with selected language IDs:", selectedLanguages);
});

function renderGenresCheckboxes(genres) {
    const container1 = document.getElementById('genre-container');
    container1.innerHTML = genres.map(genre => `
<label>
    <input type="checkbox" name="genres" id="${genre.genreId}" value="${genre.genreName}">
    ${genre.genreName}
</label>
`).join('');
}

function renderFormatsCheckboxes(formats) {
    const container2 = document.getElementById('format-container');
    container2.innerHTML = formats.map(format => `
<label>
    <input type="checkbox" name="formats" id="${format.formatId}" value="${format.formatName}">
    ${format.formatName}
</label>
`).join('');
}

function fetchMovieById(movieId) {
    $.get(`${CONFIG.baseURL}/getMovie/${movieId}`, function (data) {
        existingMovieData = data;
        if (crewMembers.length && designations.length) {
            populateFormWithMovieData(existingMovieData);
        }
    });
}

function createCastRow() {
    const rowId = `cast-${castCount++}`;

    const nameOptions = crewMembers.map(n => `<option value="${n.crewId}">${n.crewName}</option>`).join('');
    const designationOptions = designations.map(d => `<option value="${d.designationId}">${d.designationName}</option>`).join('');

    const rowHtml = `
    <div class="cast-row" id="${rowId}">
      <select class="crew-dropdown" name="name">
        <option value="">Select Name</option>
        ${nameOptions}
      </select>

      <select class="designation-dropdown" name="designation">
        <option value="">Select Designation</option>
        ${designationOptions}
      </select>

      <input type="text" class="character-name" name="characterName" placeholder="Character Name" />

      <button type="button" class="removeBtn">–</button>
    </div>
  `;

    $('#castContainer').append(rowHtml);
}

document.getElementById("movieForm").addEventListener("submit", function (e) {
    const hr = parseInt(document.getElementById("hours").value, 10);
    const min = parseInt(document.getElementById("minutes").value, 10);
    const sec = parseInt(document.getElementById("seconds").value, 10);

    if (hr === 0 && min === 0 && sec === 0) {
        e.preventDefault();
        alert("Movie duration cannot be 0:00:00");
        return;
    }

    duration = `${String(hr).padStart(2, '0')}:${String(min).padStart(2, '0')}:${String(sec).padStart(2, '0')}`;
});

function buildMovieData() {
    const hr = parseInt($('#hours').val(), 10);
    const min = parseInt($('#minutes').val(), 10);
    const sec = parseInt($('#seconds').val(), 10);

    if (hr === 0 && min === 0 && sec === 0) {
        alert("Movie duration cannot be 0:00:00");
        return null;  // Return null to stop submission
    }

    duration = `${String(hr).padStart(2, '0')}:${String(min).padStart(2, '0')}:${String(sec).padStart(2, '0')}`;

    const castList = [];
    $('#castContainer .cast-row').each(function () {
        const nameId = $(this).find('.crew-dropdown').val();
        const designationId = $(this).find('.designation-dropdown').val();
        const characterName = $(this).find('.character-name').val();

        if (nameId && designationId && characterName) {
            castList.push({
                crew: { crewId: nameId },
                crewDesignation: { designationId },
                characterName
            });
        }
    });

    const movieData = {
        movieTitle: $('input[name="movieTitle"]').val(),
        movieRating: $('#rating').val(),
        movieDuration: duration,
        movieReleaseDate: $('#releaseDate').val(),
        movieDescription: $('input[name="movieDescription"]').val(),
        moviePosterPath: $('input[name="moviePoster"]').val(),
        movieCrewEntries: castList,
        languages: $('input[name="language"]:checked').map(function () {
            return {languageId: +this.id};
        }).get(),
        genres: $('input[name="genres"]:checked').map(function () {
            return {genreId: +this.id};
        }).get(),
        formats: $('input[name="formats"]:checked').map(function () {
            return {formatId: +this.id};
        }).get()
    };

    if (isUpdate) movieData.movieId = existingMovieData.movieId;
    return movieData;
}

function populateFormWithMovieData(movie) {
    $('input[name="movieTitle"]').val(movie.movieTitle);
    $('#rating').val(movie.movieRating);
    $('#ratingValue').text(movie.movieRating);
    $('#releaseDate').val(movie.movieReleaseDate);
    $('input[name="movieDescription"]').val(movie.movieDescription);
    $('input[name="moviePoster"]').val(movie.moviePosterPath);

    const [hr, min, sec] = movie.movieDuration.split(":");
    $('#hours').val(hr);
    $('#minutes').val(min);
    $('#seconds').val(sec);

    (movie.languages || []).forEach(lang => $(`#${lang.languageId}`).prop('checked', true));
    (movie.genres || []).forEach(genre => $(`#${genre.genreId}`).prop('checked', true));
    (movie.formats || []).forEach(format => $(`#${format.formatId}`).prop('checked', true));

    (movie.movieCrewEntries || []).forEach(entry => {
        createCastRow(entry);
    });
}
