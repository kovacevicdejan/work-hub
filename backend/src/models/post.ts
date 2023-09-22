import mongoose from "mongoose";

const Schema = mongoose.Schema

let Post = new Schema({
    post_type: {
        type: String
    },
    creator_type: {
        type: Number
    },
    creator: {
        type: String
    },
    post_text: {
        type: String
    },
    post_image: {
        type: String
    },
    job_title: {
        type: String
    },
    page_name: {
        type: String
    },
    date_posted: {
        type: Number
    },
    comments: [
        {
            user: {
                type: String
            },
            profile_image: {
                type: String
            },
            text: {
                type: String
            }
        }
    ]
})

export default mongoose.model('Post', Post, 'post')