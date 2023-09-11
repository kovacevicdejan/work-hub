import { Int32 } from "mongodb";
import mongoose from "mongoose";

const Schema = mongoose.Schema

let Chat = new Schema({
    user1: {
        type: String
    },
    user2: {
        type: String
    },
    messages: [
        {
            user: {
                type: String
            },
            text: {
                type: String
            },
            date_sent: {
                type: Date
            }
        }
    ]
})

export default mongoose.model('Chat', Chat, 'chat')