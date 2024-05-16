import { useState } from "react"
import { UserInterface } from "../interfaces/UserInterface"
import { UserContext } from "./UserContext"

/* This component is a provider - it will PROVIDE the UserContext to all of its child components 
we will wrap this around EVERY component we render in the app.tsx, to make user data global

The children prop represents whatever components you wrao with the provider
(which will be the entire App.tsx in our case */
export const UserProvider: React.FC<any> = ({children}) => {

    //define the globally visible state (with useState)
    const [globalUserData, setGlobalUserData] = useState<UserInterface>({
        userId:0,
        username:"",
        jwt:""
    })

    /*This is what makes the global state variable AND the mutator available to all child components
    we accomplish this by wrapping our App.tsx's view with this provider

    UserContext.Provider?? We're labeling this component as the Provider for UserContext data*/
    return(
        <UserContext.Provider value={{globalUserData, setGlobalUserData}}>
            {children}
        </UserContext.Provider>
    )

}