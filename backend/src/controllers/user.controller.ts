import * as express from "express"
import User from "../models/user"
const bcrypt = require('bcrypt');

const fs = require('fs');
const folderPath = './uploads';

function getLastFile(folderPath) {
    try {
      const files = fs.readdirSync(folderPath);
  
      files.sort((a, b) => {
        return b.localeCompare(a);
      });
  
      if (files.length > 0) {
        const lastFile = files[0];
        return lastFile;
      } else {
        return null;
      }
    } catch (err) {
      throw err;
    }
}

export class UserController {
    login = (req: express.Request, res: express.Response) => {
        const email = req.body.email;
        const password = req.body.password;

        User.findOne({
            'email': email
        }, 
            (err, user) => {
                if(err)
                    res.status(500).json('Login failed');
                else if(!user)
                    res.json('Invalid credentials');
                else { 
                    bcrypt.compare(password, user.password).then(match => {
                            if(!match)
                                res.json('Invalid credentials');
                            else {
                                res.json("Login successful!");
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
        }, 
            (err, user) => {
                if(err)
                    res.status(500).json({ error: 'Login failed' });
                else if(!user)
                    res.status(401).json({ error: 'Invalid email' });
                else
                    res.json(user);
            }
        )
    }
}