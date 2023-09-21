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

jobRouter.route('/get_job_by_id/:job_id').get(
    (req, res) => new JobController().get_job_by_id(req, res)
)

jobRouter.route('/apply').post(
    (req, res) => new JobController().apply(req, res)
)

jobRouter.route('/search/:keyword').get(
    (req, res) => new JobController().search(req, res)
)

export default jobRouter