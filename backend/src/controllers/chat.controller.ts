import * as express from "express"
import Chat from "../models/chat"

export class ChatController {
    get_modified_chats = async(req: express.Request, res: express.Response) => {
        const user = req.params.user
        const timestamp = req.params.timestamp

        const chats = await Chat.find({
            $gt: {timestamp: timestamp},
            $or: [ { user1: user }, { user2: user} ] 
        })

        res.json(chats)
    }
}