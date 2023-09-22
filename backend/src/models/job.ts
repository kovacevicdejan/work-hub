import mongoose from "mongoose";

const Schema = mongoose.Schema

let Job = new Schema({
    title: {
        type: String
    },
    page: {
        type: String
    },
    page_image: {
        type: String
    },
    workplace_type: {
        type: String
    },
    location: {
        type: String
    },
    job_type: {
        type: Number
    },
    level: {
        type: String
    },
    description: {
        type: String
    },
    tech_stack: {
        type: String
    },
    deadline: {
        type: Number
    },
    area: {
        type: String
    },
    date_posted: {
        type: Number
    },
    applicants: [
        {
            user: {
                type: String
            },
        }
    ]
})

export default mongoose.model('Job', Job, 'job')