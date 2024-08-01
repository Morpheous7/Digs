

    import {LoginRespDTO} from "./profiles";

window.document.getElementById("myForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    LoginRespDTO = new FormData("myForm");
    let header;
    await fetch(`/login`, {
        method: "POST", // or 'PUT'
        headers: {
            "Content-Type": `application/x-www-form-urlencoded`, 'Accept': 'application/json'
        },
        body: JSON.stringify(LoginRespDTO),
    }).then(response => {
        const result = response.json();
        header = `Bearer ${result.getResponseHeader("Authorization")}`;
        this.headers.append("Authorization", header);
        let redirect = response.redirect("/home", response.status);


    }).then(LoginRespDTO => LoginRespDTO.header).catch(error => {
        console.error('Error:', error);
    })

});