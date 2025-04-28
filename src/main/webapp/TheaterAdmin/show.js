document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("addShowModal");
    const iframe = document.getElementById("addShowIframe");

    document.getElementById("openFormBtn").addEventListener("click", () => {
        iframe.src = "add-show.html";
        modal.style.display = "block";
    });

    document.querySelector(".close").addEventListener("click", () => {
        iframe.src = "";
        modal.style.display = "none";
    });
});