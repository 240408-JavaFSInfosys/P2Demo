import { useRef, useState } from "react"
import { PokemonInterface } from "../../interfaces/PokemonInterface"
import "./Catch.css"
import axios from "axios"
import { Pokemon } from "../Pokemon/Pokemon"
import { isButtonElement } from "react-router-dom/dist/dom"
import { useNavigate } from "react-router-dom"
import { state } from "../../globalData/store"

export const Catch: React.FC = () => {

    //A variable to store user input for finding a pokemon
    const [userInput, setUserInput] = useState(0)

    //we need to store pokemon state to use when rendering the PokemonComponent
    const [pokemon, setPokemon] = useState<PokemonInterface>({
        name:"",
        image:""
    })

    //we need our useNavigate hook to programmatically switch endpoints (which switches components)
    const navigate = useNavigate()

    //a function that stores the user input (Which we need for our GET request)
    const gatherInput = (input:any) => {
        setUserInput(input.target.value) //set the userInput to what's in the input box
    }

    //a function that sends a GET to PokeAPI based on the user's input
    const getPokemon = async () => {

        console.log(userInput)

        //sending our request to pokeAPI using the userInput as the pokemon id to search for
        const response = await axios.get("https://pokeapi.co/api/v2/pokemon/" + userInput)

        console.log(response.data) //response.data will give us the incoming data from the request

        //let's set our pokemon state with the incoming data
        setPokemon((pokemon) => ({...pokemon, name:response.data.name})) //only changing the name
        setPokemon((pokemon) => ({...pokemon, image:response.data.sprites.front_default})) //only changing the image

        //what's that?^^ when we have state as entire objects, it's tricky to change just one value...
        //we can use the ...spread operator to say "keep the entire state object as is, but change this one thing"

    }

    //this function will send the existing pokemon to the Database
    const catchPokemon = async () => {

        console.log("Bearer: " + state.userSessionData.jwt)

        const response = await axios.post("http://localhost:8080/pokemon", pokemon,
            {
              withCredentials: true,
              headers: {
                'Authorization': 'Bearer: ' + state.userSessionData.jwt
              }
            }
          )
        .then((response) => {
            alert(state.userSessionData.username + " caught " + pokemon.name) 
            //{username} caught {pokemonname}!
            //just cause you can doesn't mean you should...
            //wouldn't "response.data" be way shorter and simpler to write? yes
        })
        .then(() => {
            state.lastCaughtPokemon = pokemon //sending our local state to global state 
        })

        //just to show the stored data
        console.log(state)

    }

    return(
        <div className="home-page">

            <div className="navbar">
                <button className="poke-button" onClick={() => {navigate("/collection")}}>See All Pokemon</button>
                <button className="poke-button" onClick={() => {navigate("/")}}>Back to Login</button>
            </div>

            <div className="home-container">
                <h3>Search For a Pokemon!</h3>
                <input type="number" placeholder="Enter Pokemon ID" onChange={gatherInput}/>
                <button className="poke-button" onClick={getPokemon}>Find Pokemon</button>


                <div className="poke-container">
                    {pokemon.name ? <button className="poke-button" onClick={catchPokemon}>catch</button> : ''}
                    <Pokemon {...pokemon}></Pokemon>
                </div>

            </div>
        </div>
    )
}
