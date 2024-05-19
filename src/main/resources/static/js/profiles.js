



 let user = {id:"", username:"", password:"", email:"", authorities:"", phn:""};
 const event_type = {id:"", event_type:""};

 const event ={id:"", event_type, event_organizer:user, event_location:"", eventStart:Date, eventStart_Time:Date,
    eventEnd:Date, eventEnd_Time: Date, display_StartTime: Boolean, single_Event:Boolean  };


 let securityUser = {username:"", password:""};

 let LoginRespDTO = { securityUser};

 let RegisDTO ={user};

export {user,event,securityUser,event_type, LoginRespDTO, RegisDTO};

