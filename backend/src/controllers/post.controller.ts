import * as express from "express"
import Post from "../models/post"
const bcrypt = require('bcrypt');

export class PostController {
    new_post = async (req: express.Request, res: express.Response) => {
        const post = new Post({
            visibility: req.body.visibility,
            post_type: req.body.post_type,
            creator_type: req.body.creator_type,
            creator: req.body.creator,
            post_text: req.body.post_text,
            post_image: req.body.post_image,
            job_title: req.body.job_title,
            page_name: req.body.page_name,
            options: req.body.options,
            date_posted: Date.now()
        });

        post.save().then(post => {
            res.json('success')
        })
    }

    get_user_posts = async (req: express.Request, res: express.Response) => {
        const user = req.params.user

        let posts = await Post.find({
            creator: user
        })

        res.json(posts)
    }
}