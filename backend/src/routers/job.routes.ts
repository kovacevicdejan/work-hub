import express from 'express'
import { JobController } from '../controllers/job.controller'
const jobRouter = express.Router()

jobRouter.route('/new_job').post(
    (req, res) => new JobController().new_job(req, res)
)

jobRouter.route('/get_page_jobs/:page').get(
    (req, res) => new JobController().get_page_jobs(req, res)
)

jobRouter.route('/delete').post(
    (req, res) => new JobController().delete(req, res)
)

export default jobRouter