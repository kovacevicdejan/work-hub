import * as express from "express"
import Chat from "../models/chat"

export class ChatController {
    get_modified_chats = async(req: express.Request, res: express.Response) => {
        const user = req.params.user
        const timestamp = req.query.timestamp

        let chats = await Chat.find({
            timestamp: {$gt: timestamp},
            $or: [ { user1: user }, { user2: user} ] 
        })

        for(let i = 0; i < chats.length; i++) {
            chats[i].messages = chats[i].messages.filter(message => message.timestamp > timestamp)
        }

        res.json(chats)
    }
}