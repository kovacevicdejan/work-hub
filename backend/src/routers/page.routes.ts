import express from 'express'
import { PageController } from '../controllers/page.controller'
const pageRouter = express.Router()

pageRouter.route('/create_page').post(
    (req, res) => new PageController().create_page(req, res)
)

pageRouter.route('/get_page_by_name/:name').get(
    (req, res) => new PageController().get_page_by_name(req, res)
)

pageRouter.route('/get_recommended_pages/:email').get(
    (req, res) => new PageController().get_recommended_pages(req, res)
)

pageRouter.route('/follow').post(
    (req, res) => new PageController().follow(req, res)
)

pageRouter.route('/unfollow').post(
    (req, res) => new PageController().unfollow(req, res)
)

export default pageRouter