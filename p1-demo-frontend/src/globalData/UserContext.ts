import React from "react";
import { UserInterface } from "../interfaces/UserInterface";

//This is the default value for our global User state object
const initialState:UserInterface = {
    userId:0,
    username:"",
    jwt:""
}

/*This is our UserContext, and it includes two parameters:
1) globalUserData: this is the actual user data we want to share globally
2) setGlobalUserData: this is a function that allows us to update the global user state

*/
export const UserContext = React.createContext<{
    globalUserData:UserInterface,
    //this is a setter for global state - it expects a UserInterface object to set user data with
    setGlobalUserData: React.Dispatch<React.SetStateAction<UserInterface>>
}>({
    globalUserData:initialState, //setting the initial user data values to the defaults above
    setGlobalUserData: () => {} //this is a placeholder, we'll fully define it in the Provider
})