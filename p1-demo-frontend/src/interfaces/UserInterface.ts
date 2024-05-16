//Just like a Java Model Class, our React Interfaces typically MODEL some data/data type we intend to use
export interface UserInterface {
    userId?:number,
    username: string,
    password?: string
    jwt?: string
}