import express from 'express'
import { ChatController } from '../controllers/chat.controller'
const chatRouter = express.Router()

chatRouter.route('/get_new_chats/:user/:timestamp').get(
    (req, res) => new ChatController().get_modified_chats(req, res)
)

export default chatRouter