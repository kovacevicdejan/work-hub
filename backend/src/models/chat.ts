import mongoose from "mongoose";

const Schema = mongoose.Schema

let Chat = new Schema({
    user1: {
        type: String
    },
    user2: {
        type: String
    },
    // timestamp: {
    //     type: Number
    // },
    messages: [
        {
            user: {
                type: String
            },
            text: {
                type: String
            },
            timestamp: {
                type: Number
            }
        }
    ]
})

export default mongoose.model('Chat', Chat, 'chat')