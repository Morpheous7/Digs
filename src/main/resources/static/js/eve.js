
let user = {id:"", username:"", password:"", email:"", authorities:"", phn:""};
let ImageData = {name: "", byte: new Uint8Array( [1024] ), type: ""};

let event= { event_title:"", inputFile: ImageData, event_type:"", event_Organizer:user, event_location:"", address1:"",
    address2:"", zip:"", state:"", city:"", country:"", event_date:Date, eventStart_Time: Date, single_event: Boolean,
    repetitive_Event:Boolean, privateEvent:Boolean };
export {user,event, ImageData};
