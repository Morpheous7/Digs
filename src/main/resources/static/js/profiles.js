



let user = {id:"", username:"", password:"", email:"", authorities:"", phn:""};
let address= {event_location:"", address1:"", address2:"", zip:"", state:"", city:""};
let ImageData = {name: "", byte: new Uint8Array( [1024] ), type: ""};

let event= { event_title:"", inputFile: Blob, event_type:"", event_Organizer:user, event_location:"", address1:"",
 address2:"", zip:"", state:"", city:"", country:"", event_date:Date, eventStart_Time: Date, single_event: Boolean,
 repetitive_Event:Boolean, privateEvent:Boolean };


 let securityUser = {username:"", password:""};

 let LoginRespDTO = { securityUser};

 let RegisDTO ={user};

export {user,event,securityUser,address, LoginRespDTO, RegisDTO, ImageData};
