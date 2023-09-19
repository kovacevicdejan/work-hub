import express from 'express'
import { PageController } from '../controllers/page.controller'
const pageRouter = express.Router()

pageRouter.route('/create_page').post(
    (req, res) => new PageController().create_page(req, res)
)

pageRouter.route('/get_page_by_name/:name').get(
    (req, res) => new PageController().get_page_by_name(req, res)
)

export default pageRouter