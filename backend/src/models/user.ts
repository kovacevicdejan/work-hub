import mongoose from "mongoose";

const Schema = mongoose.Schema

let User = new Schema({
    email: {
        type: String
    },
    password: {
        type: String
    },
    firstname: {
        type: String
    },
    lastname: {
        type: String
    },
    profile_image: {
        type: String
    },
    about: {
        type: String
    },
    headline: {
        type: String
    },
    location: {
        type: String
    },
    phone_number: {
        type: String
    },
    industry: {
        type: String
    },
    registration_date: {
        type: Number
    },
    connections: [
        {
            user: {
                type: String
            }
        }
    ],
    followed_pages: [
        {
            name: {
                type: String
            }
        }
    ],
    saved_jobs: [
        {
            id: {
                type: String
            }
        }
    ],
    sent_invitations: [
        {
            user: {
                type: String
            }
        }
    ],
    received_invitations: [
        {
            user: {
                type: String
            }
        }
    ],
    experience: [
        {
            company: {
                type: String
            },
            job_title: {
                type: String
            },
            job_type: {
                type: String
            },
            start_date: {
                type: String
            },
            end_date: {
                type: String
            },
            location: {
                type: String
            }
        }
    ],
    skills: [
        {
            name: {
                type: String
            }
        }
    ]
})

export default mongoose.model('User', User, 'user')