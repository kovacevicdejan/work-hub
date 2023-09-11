import { Int32 } from "mongodb";
import mongoose from "mongoose";

const Schema = mongoose.Schema

let Job = new Schema({
    title: {
        type: String
    },
    page: {
        type: String
    },
    workplace_type: {
        type: String
    },
    location: {
        type: String
    },
    level: {
        type: String
    },
    description: {
        type: String
    },
    deadline: {
        type: Date
    },
    date_posted: {
        type: Date
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