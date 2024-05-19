/*
let div0 = document.getElementById('theAlert');
if (div0.style.display === 'none') {
    console.log('Div is not visible');
} else {
    console.log('Div is visible');
    window.location.replace("localhost:8000/user.html");
}

const alecia = window.document.getElementById("theAlert");
    if(alecia.style.display === 'hidden'){
        console.log('alecia is visible');
        window.location.replace("localhost:8000/user.html");
    }

window.addEventListener('success', () => {
    class SomeClass {
       alec = window.document.getElementById("theAlert");
        register() {
            window.addEventListener("theAlert", (evf) => {
                evf.preventDefault();
                this.someMethod(evf);
            });
        }
        someMethod(evf) {
            if (evf /!*.keyCode *!/ === this.alec) {
                // some code here…
                window.location.replace("localhost:8000/user.html");
            }
            console.log(this.alec);
        }
    }
    const myObject = new SomeClass();
    myObject.register()
});

*/


import './profiles.js';



window.document.addEventListener('submit', () => {


        // Set the date we're counting down to
        var countDownDate = new Date("Mar 14, 2024 15:50").getTime();

        // Update the count down every 1 second
        var x = setInterval(function() {

            // Get today's date and time
            var now = new Date().getTime();

            // Find the distance between now and the count down date
            var distance = countDownDate - now;

            // Time calculations for days, hours, minutes and seconds
            var days = Math.floor(distance / (1000 * 60 * 60 * 24));
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            // Output the result in an element with id="demo"
            document.getElementById("reset-button").innerHTML = days + "d " + hours + "h "
                + minutes + "m " + seconds + "s ";

            // If the count down is over, write some text
            if (distance < 0) {
                clearInterval(x);
                document.getElementById("reset-button").innerHTML = "EXPIRED";

            }
        }, 1000);
    });


    window.document.addEventListener('submit', () => {

    });
