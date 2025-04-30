function showPopupMessage(message) {
    const popup = document.getElementById("popup-message");
    popup.innerText = message;
    popup.style.display = "block";
    popup.style.position = "fixed";
    popup.style.top = "20px";
    popup.style.right = "20px";
    popup.style.padding = "10px 20px";
    popup.style.backgroundColor = "#2ecc71";
    popup.style.color = "white";
    popup.style.borderRadius = "5px";
    popup.style.boxShadow = "0px 0px 10px rgba(0,0,0,0.2)";
    popup.style.zIndex = "9999";

    setTimeout(() => {
        popup.style.display = "none";
    }, 5000);
}

function showMessage(message, isSuccess) {
    const formMessage = $('#formMessage');
    formMessage
        .text(message)
        .removeClass('text-success text-danger')
        .addClass(isSuccess ? 'text-success' : 'text-danger')
        .fadeIn();
}