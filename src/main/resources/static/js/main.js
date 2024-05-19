

    import {LoginRespDTO} from "./profiles";



window.document.getElementById("myForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    LoginRespDTO = new FormData("myForm");
    let header;
    await fetch(`/aut/logins`, {
        method: "POST", // or 'PUT'
        headers: {
            "Content-Type": `application/x-www-form-urlencoded`, 'Accept': 'application/json'
        },
        body: JSON.stringify(LoginRespDTO),
    }).then(response => {
        const result = response.json();
        header = `Bearer ${result.getResponseHeader("Authorization")}`;
        this.headers.append("Authorization", header);
        let redirect = response.redirect("/user", response.status);

//            const token = (new URL(document.location)).searchParams.get('token')
//         response.redirect(307, `http://appServer:5001/?key=value#token=${header}`);
        //request(`http://appServer:5001/?key=value`, { header}).on('response', (response) => response.pipe(res))

    }).then(LoginRespDTO => LoginRespDTO.header).catch(error => {
        console.error('Error:', error);
    })

});