import { useNavigate } from "react-router-dom"
import "./Login.css"
import { UserInterface } from "../../interfaces/UserInterface"
import { useState } from "react"
import axios from "axios"
import { state } from "../../globalData/store"

export const Login: React.FC = () => {

    //defining a state object for our user data
    const[user, setUser] = useState<UserInterface>({
        username:"",
        password:""
    })

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
            state.userSessionData = response.data

            //for visibility, print out the response data (which includes JWT)
            console.log(state.userSessionData)

            //welcome the user
            alert("Welcome, " + state.userSessionData.username)

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
