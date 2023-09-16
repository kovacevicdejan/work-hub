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
            email: {
                type: String
            }
        }
    ],
    received_invitations: [
        {
            email: {
                type: String
            }
        }
    ],
    experience: [
        {
            company: {
                type: String
            },
            positions: [
                {
                    job_title: {
                        type: String
                    },
                    job_type: {
                        type: String
                    },
                    start_date: {
                        type: Number
                    },
                    end_date: {
                        type: Number
                    },
                    location: {
                        type: String
                    },
                    description: {
                        type: String
                    }
                }
            ]
        }
    ],
    education: [
        {
            school: {
                type: String
            },
            positions: [
                {
                    title: {
                        type: String
                    },
                    start_year: {
                        type: Number
                    },
                    end_year: {
                        type: Number
                    },
                    location: {
                        type: String
                    }
                }
            ]
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