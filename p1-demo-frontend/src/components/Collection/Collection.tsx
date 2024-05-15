import { useEffect, useState } from "react"
import { PokemonInterface } from "../../interfaces/PokemonInterface"
import axios from "axios"
import { Pokemon } from "../Pokemon/Pokemon"
import "./Collection.css"
import { state } from "../../globalData/store"

export const Collection: React.FC = () => {

    //We could have stored a base URL here for cleaner requesting
    //const baseUrl = "http://localhost:8080/pokemon" 

    //we'll store state that consists of an Array of PokemonInterface objects
    const [pokemon, setPokemon] = useState<PokemonInterface[]>([]) //start with empty array

    //I want to get all pokemon when the component renders, so we'll use useEffect
    useEffect(() => {
        getAllPokemon()
    }, []) //empty array so this triggers on component load and state change

    //GET request to server to get all pokemon
    const getAllPokemon = async () => {

        //our GET request (remember to send withCredentials to confirm the user is logged in)
        const response = await axios.get("http://localhost:8080/pokemon", {
            withCredentials: true,
            headers: {
              'Authorization': 'Bearer: ' + state.userSessionData.jwt
            }
        })

        //populate the pokemon state  
        setPokemon(response.data)

        console.log(response.data)

    }

    //Delete pokemon by id
    const deletePokemon = async(pokeId:number|undefined) => {

        //TODO: throw some error if pokeId is typeof undefined

        console.log("Bearer: " + state.userSessionData.jwt)

        const response = await axios.delete("http://localhost:8080/pokemon/" + pokeId,{
            withCredentials: true,
            headers: {
              'Authorization': 'Bearer: ' + state.userSessionData.jwt
            }
        })
        .then((response) => alert(response.data))
        .then(() => getAllPokemon())
        .catch(
            //TODO: we could have some catches here for the errors that can pop up
        )

    }

    return(
        <div className="collection-container">

            {/* using map(), for every pokemon that belongs to the logged in user... 
            Display one Pokemon component, and a button to delete it*/}
            {pokemon.map((poke, index) => 
                <div>
                    <Pokemon {...poke}></Pokemon>
                    <button className="poke-button" onClick={() => deletePokemon(poke.pokeId)}>Delete</button>
                </div>
           )}

            {/* If you need to render multiple things in map(), they need to be in a <div> */}

        </div>
    )
}
