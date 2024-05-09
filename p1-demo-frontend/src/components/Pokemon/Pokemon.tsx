import { PokemonInterface } from "../../interfaces/PokemonInterface"
import "./Pokemon.css"

export const Pokemon: React.FC<PokemonInterface> = (pokemon:PokemonInterface) => {


    return(
        <div className="pokemon-container">
                <div>
                    <img src={pokemon.image} alt="POKEPIC" />
                </div>

                <h3>{pokemon.name}</h3>
        </div>
    )

}