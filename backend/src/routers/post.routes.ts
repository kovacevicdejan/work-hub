import express from 'express'
import { PostController } from '../controllers/post.controller'
const postRouter = express.Router()

postRouter.route('/new_post').post(
    (req, res) => new PostController().new_post(req, res)
)

postRouter.route('/get_user_posts/:user').get(
    (req, res) => new PostController().get_user_posts(req, res)
)

export default postRouter