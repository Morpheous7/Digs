import {event} from "./profiles";


const form = document.querySelector("#formAction");
async function sendData() {
    // Construct a FormData instance
    event = new FormData(form);
    // Add a file

    try {
        const response = await fetch( '/event/create', {
            method: "POST",
            headers:  { 'Content-Type': 'multipart/form-data' },
            // Set the FormData instance as the request body
            body: event
        });
        console.log(await response.json());
    } catch (e) {
        console.error(e);
    }
}
// Take over form submission
form.addEventListener("submit", (event) => {
    event.preventDefault();
    sendData( ).then( function (r) {
    } );
});