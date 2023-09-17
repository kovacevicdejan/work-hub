import * as express from "express"
import User from "../models/user"
const bcrypt = require('bcrypt');

const fs = require('fs');
const folderPath = './uploads';

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
                industry: req.body.industry,
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

    get_users_by_industry = (req: express.Request, res: express.Response) => {
        const industry = req.params.industry;

        User.find({
            'industry': industry
        }, (err, users) => {
            res.json(users);
        }
        )
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
}