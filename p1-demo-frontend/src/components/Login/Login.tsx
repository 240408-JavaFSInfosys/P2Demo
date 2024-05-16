import { useNavigate } from "react-router-dom"
import "./Login.css"
import { UserInterface } from "../../interfaces/UserInterface"
import { useContext, useState } from "react"
import axios from "axios"
import { state } from "../../globalData/store"
import { UserContext } from "../../globalData/UserContext"

export const Login: React.FC = () => {

    //defining a state object for our user data
    const[user, setUser] = useState<UserInterface>({
        username:"",
        password:""
    })

    //a different state object for the global user data in our UserContext
    const {globalUserData, setGlobalUserData} = useContext(UserContext)

    //we need a useNavigate hook to allow us to navigate between components... no more manual URL changes!
    const navigate = useNavigate()

    //function to store input box values
    const storeValues = (input:any) => {
 
        //if the input that has changed is the "username" input, change the value of username in the user state object
 
        if(input.target.name === "username"){
            setUser((user) => ({...user, username:input.target.value}))
        } else {
            setUser((user) => ({...user, password:input.target.value}))
        }
 
    }

    //this function will (EVENTUALLY) gather username and password, and send a POST to our java server
    const login = async () => {

        //TODO: We could (should) validate user input here as well as backend 

        //Send a POST request to the backend for login
        //NOTE: with credentials is what lets us save/send user session info
        const response = await axios.post("http://localhost:8080/users/login", 
        user,
        {withCredentials:true})
        .then((response) => {

            //if login was successful, log the user in and store their info in global state
            //state.userSessionData = response.data <- Old global state file logic
            setGlobalUserData(response.data)

            //for visibility, print out the response data (which includes JWT)
            //console.log(state.userSessionData)
            console.log(globalUserData)

            //welcome the user
            //alert("Welcome, " + state.userSessionData.username)
            alert("Welcome, " + globalUserData.username + " from Context API")

            //TODO: the log and alert won't show our data... due to the async nature of axios
            //we COULD make a useEffect that listens for change in globalUserData
            //then logs to the console, prints an alert, etc. but no big deal here

            //use our useNavigate hook to switch views to the Catch Pokemon Component
            navigate("/catch")

        })
        .catch((error) => {alert("Login Failed!")}
    ) //If login fails, tell the user that

    }


    return(
        <div className="login">
            <div className="text-container">
                <h1>Welcome to the Pokemon Exchange</h1>
                <h3>Sign in to Catch and View Pokemon!</h3>

                <div className="input-container">
                    <input type="text" placeholder="username" name="username" onChange={storeValues}/>
                </div>

                <div className="input-container">
                    <input type="password" placeholder="password" name="password" onChange={storeValues}/>
                </div>

                <button className="login-button" onClick={login}>Login</button>
                <button className="login-button" onClick={() => navigate("/register")}>Create Account</button>
            </div>

            {/* Conditional Rendering to display last caught poke from global storage */}
            {state.lastCaughtPokemon.image ? <div>
                <h3>Last Caught Pokemon:</h3>
                <img src={state.lastCaughtPokemon.image} alt="POKEMON PIC" />
            </div>:<h3>You haven't caught a Pokemon recently...</h3>}

        </div>
 
    )

}
