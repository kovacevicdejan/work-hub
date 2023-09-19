import mongoose from "mongoose";

const Schema = mongoose.Schema

let Post = new Schema({
    visibility: {
        type: String
    },
    post_type: {
        type: String
    },
    creator_type: {
        type: String
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
    options: [
        {
            text: {
                type: String
            },
            voters: [
                {
                    user: {
                        type: String
                    }
                }
            ]
        }
    ],
    date_posted: {
        type: Number
    },
    comments: [
        {
            user: {
                type: String
            },
            text: {
                type: String
            },
            replying_user: {
                type: String
            },
            replying_text: {
                type: String
            }
        }
    ],
    likes: [
        {
            user: {
                type: String
            }
        }
    ]
})

export default mongoose.model('Post', Post, 'post')