
import {RegisDTO} from "./profiles";

window.document.getElementById(`formAction`).addEventListener(`submit`, async (e) => {
    e.preventDefault();
    RegisDTO = new FormData(`formAction`);
    await fetch("/aut/registers", {
        method: 'POST', // or 'PUT'
        headers: {
            Accept: 'application/json',
            'Content-Type': `application/x-www-form-urlencoded`
        },
        redirect: '/aut/registers',
        body: JSON.stringify(RegisDTO),
    }).then(response => {
        if(response.status === 200) {
            response.redirect("/user", response.status);
            window.document.location.href= "/user";
        }
    }).then(RegisDTO => RegisDTO.header).catch(error => {
        console.error('Error:', error);
        //console.log("Success:", result);
    })
});



/*
.then(response => {
    const result = response.json();
    header = 'Bearer ' + result.getResponseHeader("Authorization");
    console.log("Success:", result);
    window.location.href = "/register.html";
//            const token = (new URL(document.location)).searchParams.get('token')
    // res.redirect(307, `http://appServer:5001/?key=value#token=${jwt}`)!
//            http.request(`http://appServer:5001/?key=value`, { 'Authorization': 'Bearer ' + jwt }).on('response', (response) => response.pipe(res))
//            res.redirect(307, `http://appServer:5001/?key=value#token=${jwt}`)
    //window.location.href = "/register";
}).then(data => data.header)*/
