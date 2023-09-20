import * as express from "express"
import User from "../models/user"
const bcrypt = require('bcrypt');

export class UserController {
    sign_in = (req: express.Request, res: express.Response) => {
        const email = req.body.email;
        const password = req.body.password;

        User.findOne({
            'email': email
        },
            (err, user) => {
                if (err)
                    res.status(500).json('Sign In failed');
                else if (!user)
                    res.json('Invalid credentials');
                else {
                    bcrypt.compare(password, user.password).then(match => {
                        if (!match)
                            res.json('Invalid credentials');
                        else {
                            res.json("Sign In successful!");
                        }
                    }
                    )
                }
            }
        )
    }

    register = (req: express.Request, res: express.Response) => {
        bcrypt.hash(req.body.password, 10).then(hashed => {
            const user = new User({
                email: req.body.email,
                password: hashed,
                firstname: req.body.firstname,
                lastname: req.body.lastname,
                profile_image: req.body.profile_image,
                about: req.body.about,
                headline: req.body.headline,
                location: req.body.location,
                phone_number: req.body.phone_number,
                interests: req.body.interests,
                registration_date: Date.now(),
            });

            user.save().then(user => {
                res.status(200).json({ message: 'User registered successfully!' });
            }).catch(err => {
                res.status(500).json({ error: 'Registration failed!' });
            })
        })
    }

    get_user_by_email = (req: express.Request, res: express.Response) => {
        const email = req.params.email;

        User.findOne({
            'email': email
        }, (err, user) => {
            if (err)
                res.status(500).json({ error: 'Login failed' });
            else if (!user)
                res.json('Invalid email');
            else
                res.json(user);
        }
        )
    }

    get_recommended_users = async (req: express.Request, res: express.Response) => {
        const email = req.params.email;
        const user = await User.findOne({email: email})
        const connections = user.connections.map(conn => conn.user)
        const interests = user.interests.split(", ")

        User.find({
            email: {$ne: email, $nin: connections}
        }, (err, users) => {
            res.json(users);
        })
    }

    connect = (req: express.Request, res: express.Response) => {
        const email1 = req.body.user1;
        const email2 = req.body.user2;

        User.findOneAndUpdate({
            'email': email1
        }, {
            $push: { sent_invitations: { 'user': email2 } }
        }, (err, user1) => {
            User.findOneAndUpdate({
                'email': email2
            }, {
                $push: { received_invitations: { 'user': email1 } }
            }, (err, user1) => {
                res.json('success')
            })
        }
        )
    }

    accept_invitation = async (req: express.Request, res: express.Response) => {
        const email1 = req.body.user1;
        const email2 = req.body.user2;

        await User.findOneAndUpdate({
            'email': email1
        }, {
            $push: { connections: { 'user': email2 } },
            $pull: {received_invitations: {user: email2}}
        })

        await User.findOneAndUpdate({
            'email': email2
        }, {
            $push: { connections: { 'user': email1 } },
            $pull: {sent_invitations: {user: email1}}
        })

        res.json("success")
    }

    decline_invitation = async (req: express.Request, res: express.Response) => {
        const email1 = req.body.user1;
        const email2 = req.body.user2;

        await User.findOneAndUpdate({
            'email': email1
        }, {
            $pull: {received_invitations: {user: email2}}
        })

        await User.findOneAndUpdate({
            'email': email2
        }, {
            $pull: {sent_invitations: {user: email1}}
        })

        res.json("success")
    }

    withdraw_invitation = async (req: express.Request, res: express.Response) => {
        const email1 = req.body.user1;
        const email2 = req.body.user2;

        await User.findOneAndUpdate({
            'email': email1
        }, {
            $pull: {sent_invitations: {user: email2}}
        })

        await User.findOneAndUpdate({
            'email': email2
        }, {
            $pull: {received_invitations: {user: email1}}
        })

        res.json("success")
    }

    remove_connection = async (req: express.Request, res: express.Response) => {
        const email1 = req.body.user1;
        const email2 = req.body.user2;

        await User.findOneAndUpdate({
            'email': email1
        }, {
            $pull: {connections: {user: email2}}
        })

        await User.findOneAndUpdate({
            'email': email2
        }, {
            $pull: {connections: {user: email1}}
        })

        res.json("success")
    }

    edit_profile = async (req: express.Request, res: express.Response) => {
        await User.findOneAndUpdate({
            email: req.body.email
        }, {
            firstname: req.body.firstname,
            lastname: req.body.lastname,
            about: req.body.about,
            headline: req.body.headline,
            location: req.body.location,
            phone_number: req.body.phone_number
        })

        res.json("success")
    }

    add_skill = async (req: express.Request, res: express.Response) => {
        await User.findOneAndUpdate({
            email: req.body.user
        }, {
            $push: {skills: {name: req.body.skill}}
        })

        res.json("success")
    }

    add_experience = async (req: express.Request, res: express.Response) => {
        await User.findOneAndUpdate({
            email: req.body.email
        }, {
            $push: {experience: {
                company: req.body.company,
                job_title: req.body.job_title,
                job_type: req.body.job_type,
                start_date: req.body.start_date,
                end_date: req.body.end_date,
                location: req.body.location
            }}
        })

        res.json("success")
    }
}