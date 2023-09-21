import * as express from "express"
import Job from "../models/job"

export class JobController {
    new_job = async (req: express.Request, res: express.Response) => {
        const job = new Job({
            title: req.body.title,
            page: req.body.page,
            workplace_type: req.body.workplace_type,
            location: req.body.location,
            job_type: req.body.job_type,
            level: req.body.level,
            description: req.body.description,
            tech_stack: req.body.tech_stack,
            deadline: req.body.deadline,
            area: req.body.area,
            date_posted: Date.now()
        });

        job.save().then(job => {
            res.json('success')
        })
    }

    get_page_jobs = async (req: express.Request, res: express.Response) => {
        const page = req.params.page

        let jobs = await Job.find({
            page: page
        })

        res.json(jobs)
    }

    delete = async (req: express.Request, res: express.Response) => {
        const job_id = req.body.job_id

        await Job.findOneAndDelete({
            _id: job_id
        })

        res.json('success')
    }

    get_job_by_id = async (req: express.Request, res: express.Response) => {
        const job_id = req.params.job_id;

        const job = await Job.findOne({
            _id: job_id
        })

        res.json(job)
    }

    apply = async (req: express.Request, res: express.Response) => {
        const user = req.body.user
        const job_id = req.body.job_id

        await Job.findOneAndUpdate({
            _id: job_id
        }, {
            $push: {applicants: {user: user}}
        })

        res.json('success')
    }

    search = async (req: express.Request, res: express.Response) => {
        const keyword = req.params.keyword

        const users = await Job.find({
            title: { $regex: keyword, $options: 'i' }
        })

        res.json(users)
    }
}