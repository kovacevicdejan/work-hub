import mongoose from "mongoose";

const Schema = mongoose.Schema

let Page = new Schema({
    name: {
        type: String
    },
    headline: {
        type: String
    },
    industry: {
        type: String
    },
    location: {
        type: String
    },
    profile_image: {
        type: String
    },
    about: {
        type: String
    },
    website: {
        type: String
    },
    date_created: {
        type: Number
    },
    admin: {
        type: String
    },
    specialties: {
        type: String
    },
    followers: [
        {
            user: {
                type: String
            }
        }
    ],
    reviews: [
        {
            user: {
                type: String
            },
            text: {
                type: String
            },
            user_image: {
                type: String
            }
        }
    ]
})

export default mongoose.model('Page', Page, 'page')
