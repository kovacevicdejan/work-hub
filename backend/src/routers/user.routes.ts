import express from 'express'
import { UserController } from '../controllers/user.controller'
const userRouter = express.Router()

userRouter.route('/login').post(
    (req, res) => new UserController().login(req, res)
)

userRouter.route('/register').post(
    (req, res) => new UserController().register(req, res)
)

userRouter.route('/get_user_by_email/:email').get(
    (req, res) => new UserController().get_user_by_email(req, res)
)

export default userRouter