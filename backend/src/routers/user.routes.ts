import express from 'express'
import { UserController } from '../controllers/user.controller'
const userRouter = express.Router()

userRouter.route('/sign_in').post(
    (req, res) => new UserController().sign_in(req, res)
)

userRouter.route('/register').post(
    (req, res) => new UserController().register(req, res)
)

userRouter.route('/get_user_by_email/:email').get(
    (req, res) => new UserController().get_user_by_email(req, res)
)

userRouter.route('/get_users_by_industry/:industry').get(
    (req, res) => new UserController().get_users_by_industry(req, res)
)

userRouter.route('/connect').post(
    (req, res) => new UserController().connect(req, res)
)

userRouter.route('/accept_invitation').post(
    (req, res) => new UserController().accept_invitation(req, res)
)

userRouter.route('/decline_invitation').post(
    (req, res) => new UserController().decline_invitation(req, res)
)

userRouter.route('/withdraw_invitation').post(
    (req, res) => new UserController().withdraw_invitation(req, res)
)

userRouter.route('/remove_connection').post(
    (req, res) => new UserController().remove_connection(req, res)
)

export default userRouter